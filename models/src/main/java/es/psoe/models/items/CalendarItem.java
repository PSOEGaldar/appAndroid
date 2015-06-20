package es.psoe.models.items;

import java.util.Date;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class CalendarItem {
    public Date date;
    private String title;
    public  String location;
    public  String description;

    public String getTitle() {
        return title.toUpperCase();
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
