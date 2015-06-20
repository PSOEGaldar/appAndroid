package es.psoe.psoegaldar.views.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import es.psoe.models.items.CalendarItem;
import es.psoe.presenters.CalendarPresenter;
import es.psoe.psoegaldar.AppAbstract;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.views.fragments.modules.CalendarFragmentModule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CalendarFragmentTest extends ActivityUnitTestCase<TestingFragmentsActivity> {
    private FragmentManager fragmentManager;
    private CalendarFragment calendarFragment;
    private TestingFragmentsActivity mainActivity;
    private Context mContext;
    private ProgressBar progressBar;
    private TextView textView_no_network;
    private TextView textView_no_calendar_events;
    private ListView listView;
    @Inject CalendarPresenter calendarPresenter;

    @Module(injects = CalendarFragmentTest.class,
            includes = CalendarFragmentModule.class,
            overrides = true)
    public static class CalendarFragmentModuleTest{
        CalendarFragmentModuleTest(){}
        @Provides @Singleton public CalendarPresenter provideCalendarPresenter(){
            return mock(CalendarPresenter.class);
        }
    }

    public CalendarFragmentTest() {
        super(TestingFragmentsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        AppAbstract.objectGraph = ObjectGraph.create(new CalendarFragmentModuleTest());

        mContext = new ContextWrapper(getInstrumentation().getTargetContext());
        mContext.setTheme(R.style.Theme_AppCompat);
        mContext.getApplicationContext();
        mainActivity = (TestingFragmentsActivity) launchActivity(mContext.getPackageName(), TestingFragmentsActivity.class, null);
        AppAbstract.objectGraph.inject(this);

        fragmentManager = mainActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, new CalendarFragment())
                .commit();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                fragmentManager.executePendingTransactions();
            }
        });

        calendarFragment = (CalendarFragment) fragmentManager.findFragmentById(android.R.id.content);
        progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        textView_no_network = (TextView) mainActivity.findViewById(R.id.no_network);
        textView_no_calendar_events = (TextView) mainActivity.findViewById(R.id.no_calendar_events);
        listView = (ListView) mainActivity.findViewById(R.id.listViewCalendar);
    }

    @Override
    public void tearDown() throws Exception {
        progressBar = null;
        textView_no_network = null;
        textView_no_calendar_events = null;
        listView = null;
        calendarFragment = null;
        calendarPresenter = null;
        mainActivity = null;
        super.tearDown();
    }

    public void testPreconditions() throws Exception {
        assertNotNull(mainActivity);
        assertNotNull(calendarFragment);
        assertNotNull(progressBar);
        assertNotNull(textView_no_network);
        assertNotNull(textView_no_calendar_events);
        assertNotNull(listView);
    }

    public void testCallGetCalendar() throws Exception {
        verify(calendarPresenter, times(1)).GetCalendar();
    }

    public void testGetURLFeed() throws Exception {
        String expected = mainActivity.getResources().getString(R.string.url_calendar);
        assertEquals(calendarFragment.GetURLFeed(), expected);
    }

    public void testInitialVisibility() throws Exception {
        assertTrue(View.INVISIBLE == textView_no_network.getVisibility());
        assertTrue(View.INVISIBLE == textView_no_calendar_events.getVisibility());
        assertTrue(View.VISIBLE == progressBar.getVisibility());
        assertTrue(View.INVISIBLE == listView.getVisibility());
    }

    public void testShowListCalendarEmpty() {
        final List<CalendarItem> list = new ArrayList<>();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                calendarFragment.ReadCalendar(list);
            }
        });

        assertTrue(View.INVISIBLE == textView_no_network.getVisibility());
        assertTrue(View.INVISIBLE == progressBar.getVisibility());
        assertTrue(View.INVISIBLE == listView.getVisibility());
        assertTrue(View.VISIBLE == textView_no_calendar_events.getVisibility());
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

    private void showList() {
        final List<CalendarItem> list = createListCalendar();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                calendarFragment.ReadCalendar(list);
            }
        });
    }

    public void testShowCalendarList() throws Exception {

        showList();
        assertTrue(View.INVISIBLE == textView_no_network.getVisibility());
        assertTrue(View.INVISIBLE == textView_no_calendar_events.getVisibility());
        assertTrue(View.INVISIBLE == progressBar.getVisibility());
        assertTrue(View.VISIBLE == listView.getVisibility());
    }
}