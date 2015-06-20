package es.psoe.presenters;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import es.psoe.models.donwloads.DownloadCalendar;
import es.psoe.models.donwloads.interfaces.DownloadCalendarInterface;
import es.psoe.models.items.CalendarItem;
import es.psoe.presenters.interfaces.CalendarPresenterInterface;
import es.psoe.presenters.modules.CalendarPresenterModule;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class CalendarPresenter  implements DownloadCalendarInterface {
    private final CalendarPresenterInterface activity;
    private ObjectGraph objectGraph;
    @Inject DownloadCalendar feed;

    public CalendarPresenter(CalendarPresenterInterface activity) {
        this.activity = activity;
        objectGraph = getObjectGraph().plus(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new CalendarPresenterModule(this));
    }

    public ObjectGraph getObjectGraph(){
        return activity.getObjectGraph();
    }

    @Override
    public void processFinish(List<CalendarItem> output) {
        activity.ReadCalendar(output);
    }

    @Override
    public Boolean isVisible() {
        return activity.isViewVisible();
    }

    public void GetCalendar()
    {
       if(!activity.isNetwork()){
            activity.setNoNetwork();
            return;
        }
        try {
            feed.execute(new URL(activity.GetURLFeed()));
        }catch (Exception e){
           // Log.v("PSOEGaldar", "feedPresenter: " + e.toString());
        }
    }
}
