package es.psoe.psoegaldar.views.activities;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Menu;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import es.psoe.presenters.ObjectGraphInterface;
import es.psoe.presenters.interfaces.MainPresenterInterface;
import es.psoe.psoegaldar.App;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.helpers.CustomTypefaceSpan;
import es.psoe.psoegaldar.views.activities.common.ActivityRoot;
import es.psoe.psoegaldar.views.activities.modules.MainActivityModule;
import es.psoe.psoegaldar.views.fragments.CalendarFragment;
import es.psoe.psoegaldar.views.fragments.NavigationDrawerFragment;
import es.psoe.psoegaldar.views.fragments.NewsFeedFragment;
import es.psoe.psoegaldar.views.fragments.WhereAreFragment;

public class MainActivity extends ActivityRoot
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, MainPresenterInterface{
    @Inject
    es.psoe.presenters.MainPresenter presenter;
    private ObjectGraph activityGraph;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectGraph.inject(this);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainActivityModule(this));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        presenter.selectedFragment(position);
    }

    /*public void onSectionAttached(int number) {
        mTitle = presenter.sectionAttached(number);
    }*/

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        SpannableString text = new SpannableString(mTitle);
        text.setSpan(new CustomTypefaceSpan(
                Typeface.createFromAsset(getAssets(), "fonts/INU____.TTF")),
                0,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public String[] getSectionNames() {
        return getApplication().getResources().getStringArray(R.array.section_names);
    }

    @Override
    public Fragment getNewsFeedFragment() {
        return new NewsFeedFragment();
    }

    @Override
    public Fragment getCalendarFragment() {
        return new CalendarFragment();
    }

    @Override
    public Fragment getWhereAreFragment() {
        return new WhereAreFragment();
    }

    @Override
    public void sendMail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.email_address), null));
        startActivity(Intent.createChooser(intent, "Send Email..."));
    }

    @Override
    public void getProgramActivity() {
        Intent intent = new Intent("es.psoe.psoegaldar.views.activities.ProgramActivity");
        startActivity(intent);
    }

    @Override
    public void getFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
