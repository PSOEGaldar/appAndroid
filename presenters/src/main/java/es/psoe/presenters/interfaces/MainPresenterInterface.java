package es.psoe.presenters.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public interface MainPresenterInterface{
    public String[] getSectionNames();
    public Fragment getNewsFeedFragment();
    public Fragment getCalendarFragment();
    public Fragment getWhereAreFragment();
    public void sendMail();
    public void getProgramActivity();
    public void getFragment(Fragment fragment);
}
