package es.psoe.presenters.interfaces;

import android.content.Context;

import java.util.List;

import dagger.ObjectGraph;
import es.psoe.models.items.FeedItem;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public interface NewsFeedPresenterInterface {
    public void ReadUpdates(List<FeedItem> feed);
    public String GetURLFeed();
    public void setNoNetwork();
    public Context getBaseContext();
    public Boolean isViewVisible();
    public ObjectGraph getObjectGraph();
    public boolean isNetwork();
}
