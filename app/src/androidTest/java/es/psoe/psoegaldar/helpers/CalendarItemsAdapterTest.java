package es.psoe.psoegaldar.helpers;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.psoe.models.items.CalendarItem;
import es.psoe.psoegaldar.R;

public class CalendarItemsAdapterTest extends AndroidTestCase {
    private CalendarItemsAdapter calendarItemsAdapter;
    private final List<CalendarItem> list;

    public CalendarItemsAdapterTest() {
        super();
        list = createListCalendar();
    }

    private List<CalendarItem> createListCalendar(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        List<CalendarItem> list = new ArrayList<>();
        CalendarItem calendarItem = new CalendarItem();
        try {
            calendarItem.setTitle("Title 1");
            calendarItem.date = dateFormat.parse("20150331T193000Z");
            calendarItem.location = "Location 1";
            calendarItem.description = "Description 1";
            list.add(calendarItem);

            calendarItem.setTitle("Title 2");
            calendarItem.date = dateFormat.parse("20150401T193000Z");
            calendarItem.location = "Location 2";
            calendarItem.description = "Description 2";
            list.add(calendarItem);

            calendarItem.setTitle("Title 3");
            calendarItem.date = dateFormat.parse("20150402T193000Z");
            calendarItem.location = "Location 3";
            calendarItem.description = "Description 3";
            list.add(calendarItem);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setUp() throws Exception {
        super.setUp();
        calendarItemsAdapter = new CalendarItemsAdapter(getContext(), list);
    }

    public void tearDown() throws Exception {
        calendarItemsAdapter = null;
    }

    public void testGetCount() throws Exception {
        final int expected = 3;
        assertEquals(expected, calendarItemsAdapter.getCount());
    }

    public void testGetItem() throws Exception {
        for(int i=0; i<3;i++)
            assertSame(list.get(i), calendarItemsAdapter.getItem(i));
    }

    public void testGetItemId() throws Exception {
        for(int i=0; i<3;i++)
            assertEquals(i, calendarItemsAdapter.getItemId(i));
    }

    public void testGetView() throws Exception {
        for(int i=0; i<3;i++){
            View view = calendarItemsAdapter.getView(i,null,null);
            TextView textViewDayOfWeek = (TextView) view.findViewById(R.id.calendar_item_day_of_week);
            TextView textViewDate = (TextView) view.findViewById(R.id.calendar_item_date);
            TextView textViewHour = (TextView) view.findViewById(R.id.calendar_item_hour);
            TextView textViewTitle = (TextView) view.findViewById(R.id.calendar_item_title);
            TextView textViewDescription = (TextView) view.findViewById(R.id.calendar_item_description);
            TextView textViewLocation = (TextView) view.findViewById(R.id.calendar_item_location);
            assertNotNull(view);
            assertNotNull(textViewDayOfWeek);
            assertNotNull(textViewDate);
            assertNotNull(textViewHour);
            assertNotNull(textViewTitle);
            assertNotNull(textViewDescription);
            assertNotNull(textViewLocation);
            assertEquals(getDayOfWeek(list.get(i).date), textViewDayOfWeek.getText());
            assertEquals(getDayOfMonth(list.get(i).date), textViewDate.getText());
            assertEquals(getHour(list.get(i).date), textViewHour.getText());
            assertEquals(list.get(i).getTitle(), textViewTitle.getText());
            assertEquals(list.get(i).description, textViewDescription.getText());
            assertEquals(list.get(i).location, textViewLocation.getText());
        }
    }

    private String getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, new Locale("es", "ES")).toUpperCase();
    }

    private String getDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) + " de " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, new Locale("es", "ES"));
    }

    private String getHour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String hour     = (calendar.get(Calendar.HOUR_OF_DAY) >9 ? "" :"0") + calendar.get(Calendar.HOUR_OF_DAY);
        String minute   = (calendar.get(Calendar.MINUTE) >9 ? "" :"0") + calendar.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }
}