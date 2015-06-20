package es.psoe.psoegaldar.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import es.psoe.models.items.CalendarItem;
import es.psoe.presenters.CalendarPresenter;
import es.psoe.presenters.ObjectGraphInterface;
import es.psoe.presenters.interfaces.CalendarPresenterInterface;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.helpers.CalendarItemsAdapter;
import es.psoe.psoegaldar.views.fragments.common.FragmentRoot;
import es.psoe.psoegaldar.views.fragments.modules.CalendarFragmentModule;

public class CalendarFragment extends FragmentRoot implements CalendarPresenterInterface {
    @Inject CalendarPresenter calendarPresenter;
    private ListView listViewCalendar=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        listViewCalendar = (ListView)(getActivity().findViewById(R.id.listViewCalendar));
        calendarPresenter.GetCalendar();
    }

    @Override
    public String GetURLFeed()  {
        return getString(R.string.url_calendar);
    }

    @Override
    public void ReadCalendar(List<CalendarItem> items) {
        ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        TextView textView = (TextView) getActivity().findViewById(R.id.no_network);
        textView.setVisibility(View.INVISIBLE);

        if(items.size()!=0) {
            CalendarItemsAdapter calendarItemsAdapter = new CalendarItemsAdapter(getActivity().getApplicationContext(), items);
            listViewCalendar.setAdapter(calendarItemsAdapter);
            listViewCalendar.setVisibility(View.VISIBLE);
        } else {
            TextView textView1 = (TextView) getActivity().findViewById(R.id.no_calendar_events);
            textView1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getBaseContext() {
        return getActivity().getBaseContext();
    }

    @Override
    public Boolean isViewVisible() {
        return isVisible();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CalendarFragmentModule(this));
    }
}