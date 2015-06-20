package es.psoe.psoegaldar.views.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentManager;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import es.psoe.models.items.FeedItem;
import es.psoe.presenters.NewsFeedPresenter;
import es.psoe.psoegaldar.AppAbstract;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.views.fragments.modules.NewsFeedFragmentModule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NewsFeedFragmentTest extends ActivityUnitTestCase<TestingFragmentsActivity> {
    FragmentManager fragmentManager;
    NewsFeedFragment newsFeedFragment;
    @Inject NewsFeedPresenter newsFeedPresenter;
    private ProgressBar progressBar;
    private TextView textView_no_network;
    private TextView textView_no_new_feeds;
    private ListView listView;
    private Context mContext;
    private TestingFragmentsActivity mainActivity;

    @Module(injects = NewsFeedFragmentTest.class,
            includes = NewsFeedFragmentModule.class,
            overrides = true)
    public static class NewsFeedFragmentModuleTest{
        NewsFeedFragmentModuleTest(){}
        @Provides
        @Singleton
        public NewsFeedPresenter provideNewsFeedPresenter(){
            return mock(NewsFeedPresenter.class);
        }
    }

    public NewsFeedFragmentTest() {
        super(TestingFragmentsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        AppAbstract.objectGraph = ObjectGraph.create(new NewsFeedFragmentModuleTest());

        mContext = new ContextWrapper(getInstrumentation().getTargetContext());
        mContext.setTheme(R.style.Theme_AppCompat);
        mContext.getApplicationContext();
        mainActivity = (TestingFragmentsActivity) launchActivity(mContext.getPackageName(), TestingFragmentsActivity.class, null);
        AppAbstract.objectGraph.inject(this);

        fragmentManager = mainActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, new NewsFeedFragment())
                .commit();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                fragmentManager.executePendingTransactions();
            }
        });

        newsFeedFragment = (NewsFeedFragment) fragmentManager.findFragmentById(android.R.id.content);
        progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        textView_no_network = (TextView) mainActivity.findViewById(R.id.no_network);
        textView_no_new_feeds = (TextView) mainActivity.findViewById(R.id.no_new_feeds);
        listView = (ListView) mainActivity.findViewById(R.id.listView);
    }

    @Override
    public void tearDown() throws Exception {
        progressBar = null;
        textView_no_network = null;
        textView_no_new_feeds = null;
        listView = null;
        newsFeedFragment = null;
        mainActivity = null;
        super.tearDown();
    }

    public void testPreconditions() throws Exception {
        assertNotNull(mainActivity);
        assertNotNull(newsFeedFragment);
        assertNotNull(progressBar);
        assertNotNull(textView_no_network);
        assertNotNull(textView_no_new_feeds);
        assertNotNull(listView);
    }

    public void testCallGetFeed() throws Exception {
        verify(newsFeedPresenter, times(1)).GetFeed();
    }

    public void testGetURLFeed() throws Exception {
        String expected = mainActivity.getResources().getString(R.string.url_feed);
        assertEquals(newsFeedFragment.GetURLFeed(), expected);
    }

    private List<FeedItem> createListFeeds(){
        List<FeedItem> list = new ArrayList<>();
        FeedItem feedItem = new FeedItem();
        feedItem.setTitle("Title 1");
        feedItem.setStringDate("Sun, 19 Apr 2015 17:09:25");
        feedItem.setDescription("<p>Description 1</p>");
        list.add(feedItem);

        feedItem = new FeedItem();
        feedItem.setTitle("Title 2");
        feedItem.setStringDate("Mon, 20 Apr 2015 17:09:25");
        feedItem.setDescription("<p>Description 2</p>");
        list.add(feedItem);

        feedItem = new FeedItem();
        feedItem.setTitle("Title 3");
        feedItem.setStringDate("Thu, 16 Apr 2015 19:00:36");
        feedItem.setDescription("<p>Description 3</p>");
        list.add(feedItem);
        return list;
    }

    private void showList() {
        final List<FeedItem> list = createListFeeds();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                newsFeedFragment.ReadUpdates(list);
            }
        });
    }

    public void testShowFeeds() throws Exception {

        showList();
        assertTrue(View.INVISIBLE == textView_no_network.getVisibility());
        assertTrue(View.INVISIBLE == textView_no_new_feeds.getVisibility());
        assertTrue(View.INVISIBLE == progressBar.getVisibility());
        assertTrue(View.VISIBLE == listView.getVisibility());
    }

    public void testInitialVisibility() throws Exception {
        assertTrue(View.INVISIBLE == textView_no_network.getVisibility());
        assertTrue(View.INVISIBLE == textView_no_new_feeds.getVisibility());
        assertTrue(View.VISIBLE == progressBar.getVisibility());
        assertTrue(View.INVISIBLE == listView.getVisibility());
    }

    public void testShowListEmpty() {
        final List<FeedItem> list = new ArrayList<>();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                newsFeedFragment.ReadUpdates(list);
            }
        });

        assertTrue(View.INVISIBLE == textView_no_network.getVisibility());
        assertTrue(View.INVISIBLE == progressBar.getVisibility());
        assertTrue(View.INVISIBLE == listView.getVisibility());
        assertTrue(View.VISIBLE == textView_no_new_feeds.getVisibility());
    }
}