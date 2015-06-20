package es.psoe.presenters;

import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.ObjectGraph;
import es.psoe.models.donwloads.DownloadFeed;
import es.psoe.models.donwloads.interfaces.DownloadFeedInterface;
import es.psoe.models.items.FeedItem;
import es.psoe.presenters.interfaces.NewsFeedPresenterInterface;
import es.psoe.presenters.modules.NewsFeedPresenterModule;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class NewsFeedPresenter implements DownloadFeedInterface {
    private final NewsFeedPresenterInterface activity;
    private ObjectGraph objectGraph;
    @Inject DownloadFeed feed;
    private List<FeedItem> items;

    public NewsFeedPresenter(NewsFeedPresenterInterface activity) {
        this.activity = activity;
        objectGraph = getObjectGraph().plus(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new NewsFeedPresenterModule(this));
    }

    public ObjectGraph getObjectGraph(){
        return activity.getObjectGraph();
    }

    @Override
    public void processFinish(List<FeedItem> output) {
        items = output;
        activity.ReadUpdates(output);
    }

    @Override
    public Boolean isVisible() {
        return activity.isViewVisible();
    }

    public void GetFeed()
    {
        if(!activity.isNetwork()){
            activity.setNoNetwork();
            return;
        }
        try {
            //DownloadFeed feed = new DownloadFeed(this);
            feed.execute(new URL(activity.GetURLFeed()));
        }catch (Exception e){
            //Log.v("PSOEGaldar", "feedPresenter: " + e.toString());
        }
    }

    public String getSummaryFeed(int position){
        return items.get(position).getDescription();
    }

    public String getTitleFeed(int position) {
        return items.get(position).getTitle();
    }

    public String getDatePost(int position){
        Date date = items.get(position).getDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String dayWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("es", "ES"));
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer year = calendar.get(Calendar.YEAR);
        char[] _dayWeek = dayWeek.toCharArray();
        _dayWeek[0] = Character.toUpperCase(_dayWeek[0]);
        return new String(_dayWeek) + ", " + day + "-" + month + "-" + year;
    }
}