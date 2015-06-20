package es.psoe.presenters;

import es.psoe.presenters.interfaces.MainPresenterInterface;

/**
 * Created by exalu_000 on 29/03/2015.
 */

public class MainPresenter {

    private final String[] sectionNames;
    private final MainPresenterInterface activity;

    public MainPresenter(MainPresenterInterface activity) {
        this.activity = activity;
        sectionNames = activity.getSectionNames();
    }

    public void selectedFragment(Integer position){
        switch (position){
            case 0:
                activity.getFragment(activity.getNewsFeedFragment());
                break;
            case 1:
                activity.getFragment(activity.getCalendarFragment());
                break;
            case 2:
                activity.getFragment(activity.getWhereAreFragment());
                break;
            case 3:
                activity.sendMail();
                break;
            case 4:
                activity.getProgramActivity();
                break;
        }

    }

    public String sectionAttached(int number){
        return sectionNames[number-1];
    }
}
