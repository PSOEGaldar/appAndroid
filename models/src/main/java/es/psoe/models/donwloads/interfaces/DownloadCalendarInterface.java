package es.psoe.models.donwloads.interfaces;

import java.util.List;

import es.psoe.models.items.CalendarItem;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public interface DownloadCalendarInterface {
    void processFinish(List<CalendarItem> output);
    Boolean isVisible();
}
