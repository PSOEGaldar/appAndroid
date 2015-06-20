package es.psoe.psoegaldar.views.fragments;

import android.support.v4.app.FragmentActivity;

import java.util.List;

import dagger.ObjectGraph;
import es.psoe.models.items.FeedItem;
import es.psoe.presenters.ObjectGraphInterface;
import es.psoe.presenters.interfaces.NewsFeedPresenterInterface;
import es.psoe.psoegaldar.App;
import es.psoe.psoegaldar.views.activities.common.ActivityRoot;

/**
 * Created by exalu_000 on 20/04/2015.
 */
public class TestingFragmentsActivity extends FragmentActivity implements NewsFeedPresenterInterface, ObjectGraphInterface {
    public static ObjectGraph objectGraph;
    public static List<Object> modules;
    public boolean networkIsPresent;

    protected List<Object> getModules() {
        return modules;
    }

    @Override
    public void ReadUpdates(List<FeedItem> feed) {

    }

    @Override
    public String GetURLFeed() {
        return null;
    }

    @Override
    public void setNoNetwork() {

    }

    @Override
    public Boolean isViewVisible() {
        return null;
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return ((App) getApplication()).getObjectGraph();
    }

    @Override
    public boolean isNetwork() {
        return networkIsPresent;
    }
}
