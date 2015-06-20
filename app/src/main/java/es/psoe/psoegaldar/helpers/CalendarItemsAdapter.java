package es.psoe.psoegaldar.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.psoe.models.items.CalendarItem;
import es.psoe.psoegaldar.R;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class CalendarItemsAdapter extends BaseAdapter {
    private final Context context;
    private final List<CalendarItem> calendarItems;
    private final Typeface fontBlack;
    private final Typeface fontBold;
    private final Typeface fontRegular;

    public CalendarItemsAdapter(Context context, List<CalendarItem> calendarItems) {
        this.context = context;
        this.calendarItems = calendarItems;
        fontBlack = Typeface.createFromAsset(context.getAssets(), "fonts/INU____.TTF");
        fontBold = Typeface.createFromAsset(context.getAssets(), "fonts/INB____.TTF");
        fontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/INR____.TTF");
    }

    @Override
    public int getCount() {
        return this.calendarItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.calendarItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.layout_calendar_item_list, null);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarItems.get(position).date);

        setTextData(convertView, R.id.calendar_item_day_of_week, getDayOfWeek(calendar));
        setFontTextView(convertView, R.id.calendar_item_day_of_week, fontBlack);
        setTextData(convertView, R.id.calendar_item_date, getDayOfMonth(calendar));
        setFontTextView(convertView, R.id.calendar_item_date, fontRegular);
        setTextData(convertView, R.id.calendar_item_hour, getHour(calendar));
        setFontTextView(convertView, R.id.calendar_item_hour, fontRegular);
        setTextData(convertView, R.id.calendar_item_title, calendarItems.get(position).getTitle());
        setFontTextView(convertView, R.id.calendar_item_title, fontBlack);
        setTextData(convertView, R.id.calendar_item_description, calendarItems.get(position).description);
        setFontTextView(convertView, R.id.calendar_item_description, fontBold);
        setTextData(convertView, R.id.calendar_item_location, calendarItems.get(position).location);
        setFontTextView(convertView, R.id.calendar_item_location, fontRegular);
        return convertView;
    }

    private void setFontTextView(View convertView, Integer resId, Typeface font){
        TextView textView = (TextView) convertView.findViewById(resId);
        textView.setTypeface(font);
    }

    private void setTextData(View convertView, Integer resId, String data){
        TextView textView = (TextView) convertView.findViewById(resId);
        if(!data.isEmpty())
            textView.setText(data);
    }

    private String getDayOfWeek(Calendar calendar){
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, new Locale("es", "ES")).toUpperCase();
    }

    private String getDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + " de " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, new Locale("es", "ES"));
    }

    private String getHour(Calendar calendar) {
        String hour     = (calendar.get(Calendar.HOUR_OF_DAY) >9 ? "" :"0") + calendar.get(Calendar.HOUR_OF_DAY);
        String minute   = (calendar.get(Calendar.MINUTE) >9 ? "" :"0") + calendar.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }
}
