package es.psoe.psoegaldar.views.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import es.psoe.psoegaldar.R;

public class ProgramActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {
    SpinnerAdapter spinnerAdapter;
    private WebView webView;
    private ArrayList<String> programFullText;
    private ArrayList<String> programItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        programItems = new ArrayList<>();
        programFullText = new ArrayList<>();
        for(String s:getResources().getStringArray(R.array.program_items)){
            String[] sArray = s.split(":");
            programFullText.add(sArray[0]);
            programItems.add(sArray[1]);
        }

        spinnerAdapter = new  ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, programItems);
        actionBar.setListNavigationCallbacks(spinnerAdapter, this);
        //programFullText = getResources().getStringArray(R.array.program_full_text);
        if (null==this.webView)
            webView = getWebView();
        showProgramFullText(0);
    }

    @Override
    protected void onResume() {
        webView.resumeTimers();
        super.onResume();
    }

    private void showProgramFullText(int i) {
        String s = null;
        try {
            InputStream fis = getAssets().open("program/"+programFullText.get(i));
            s = new Scanner(fis,"UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = getString(R.string.html_prefix)
                + s
                + getString(R.string.html_sufix);
        webView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", "");
    }

    private WebView getWebView(){
        WebView webView1 = (WebView) findViewById(R.id.program_webView);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.getSettings().setDomStorageEnabled(true);

        webView1.setWebChromeClient(new WebChromeClient() {
        });

        return webView1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_program, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        showProgramFullText(i);
        return true;
    }
}
