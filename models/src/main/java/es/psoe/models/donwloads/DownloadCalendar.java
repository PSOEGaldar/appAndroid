package es.psoe.models.donwloads;

import android.os.AsyncTask;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import es.psoe.models.donwloads.interfaces.DownloadCalendarInterface;
import es.psoe.models.items.CalendarItem;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class DownloadCalendar extends AsyncTask<URL, Integer, List<CalendarItem>> {

    private final WeakReference<DownloadCalendarInterface> delegate;

    public DownloadCalendar(DownloadCalendarInterface delegate){
        this.delegate = new WeakReference<>(delegate);
    }

    @Override
    protected List<CalendarItem> doInBackground(URL... urls) {
        List<CalendarItem> calendarItems = new ArrayList<>();

        try {
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(urls[0].openStream());
            for (Object o : calendar.getComponents()) {
                CalendarItem item = getCalendarItem((Component) o);
                if(null!=item)
                    calendarItems.add(item);
            }
        } catch (ParserException | ParseException | IOException e) {
            e.printStackTrace();
        }
        return calendarItems;
    }

    private CalendarItem getCalendarItem(Component component) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        java.util.Calendar today = new GregorianCalendar();
        java.util.Calendar thisDate = new GregorianCalendar();

        CalendarItem item = new CalendarItem();
        item.date = dateFormat.parse(component.getProperty("DTSTART").getValue());
        item.setTitle(component.getProperty("SUMMARY").getValue());
        item.location = component.getProperty("LOCATION").getValue();
        item.description = component.getProperty("DESCRIPTION").getValue();

        thisDate.setTime(item.date);
        if(today.before(thisDate))
            return item;
        return null;
    }


    protected void onPostExecute(List<CalendarItem> items) {
        // TODO: check this.exception
        // TODO: do something with the feed
        Collections.sort(items, new Comparator<CalendarItem>() {
            @Override
            public int compare(CalendarItem lhs, CalendarItem rhs) {
                return lhs.date.compareTo(rhs.date);
            }
        });
        DownloadCalendarInterface fDelegate = delegate.get();
        if(fDelegate!=null && fDelegate.isVisible()) {
            super.onPostExecute(items);
            fDelegate.processFinish(items);
        }
    }
}