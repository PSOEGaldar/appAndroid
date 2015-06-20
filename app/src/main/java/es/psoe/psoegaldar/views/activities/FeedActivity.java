package es.psoe.psoegaldar.views.activities;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.lang.reflect.Method;

import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.helpers.CustomTypefaceSpan;

public class FeedActivity extends ActionBarActivity{
    private WebView webView;

    @Override
    protected void onStart() {
        super.onStart();

        String feed = getFeed();

        setActionBar();
        setContentView(R.layout.activity_feed);

        WebView webView = getWebView();

        webView.loadDataWithBaseURL("file:///android_asset/", feed, "text/html", "UTF-8", "");
    }

    @Override
    protected void onPause() {
        webView.pauseTimers();
        callHiddenWebViewMethod("onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        webView.resumeTimers();
        callHiddenWebViewMethod("onResume");
        super.onResume();
    }

    private WebView getWebView(){
        webView = (WebView) findViewById(R.id.feed_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {});

        return webView;
    }

    private void callHiddenWebViewMethod(String name){
        if( webView != null ){
            try {
                Method method = WebView.class.getMethod(name);
                method.invoke(webView);
            } catch (Exception e) {
                Log.e("TAG", "Didn't work");
                webView.loadData("", "text/html", "utf-8");
            }
        }
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        SpannableString text = new SpannableString(getString(R.string.app_name));
        text.setSpan(new CustomTypefaceSpan(Typeface.createFromAsset(getAssets(), "fonts/INU____.TTF")), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(text);
    }

    private String getFeed(){
        return  getString(R.string.html_prefix) +
                getString(R.string.html_date_preffix)+
                getIntent().getStringExtra("datePost")+
                getString(R.string.html_date_sufix)+
                getString(R.string.html_title_prefix) +
                getIntent().getStringExtra("feedTitle") +
                getString(R.string.html_title_sufix) +
                getSummary()+
                getString(R.string.html_sufix);
    }

    private String getSummary(){
        String feed = getIntent().getStringExtra("feedSummary");
        boolean inMark = false;
        for(int i = feed.length()-1; i>0;i--){
            char token = feed.charAt(i);
            if(token=='>')
                inMark = true;
            if(token=='<')
                inMark = false;
            if(!inMark && token=='.')
            {
                feed = feed.substring(0, i) + getString(R.string.html_article_suffix) + feed.substring(i+1);
                break;
            }
        }
        return feed;
    }
}