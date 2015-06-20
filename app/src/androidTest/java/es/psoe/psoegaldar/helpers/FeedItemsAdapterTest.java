package es.psoe.psoegaldar.helpers;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.psoe.models.items.FeedItem;
import es.psoe.psoegaldar.R;

public class FeedItemsAdapterTest extends AndroidTestCase {
    private FeedItemsAdapter feedItemsAdapter;
    private final List<FeedItem> list;

    public FeedItemsAdapterTest() {
        super();
        this.list = getList();
    }

    private List<FeedItem> getList() {
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

    public void setUp() throws Exception {
        super.setUp();
        feedItemsAdapter = new FeedItemsAdapter(getContext(),list);
    }

    public void tearDown() throws Exception {
        feedItemsAdapter = null;
    }

    public void testGetCount() throws Exception {
        final int expected = 3;
        assertEquals(expected, feedItemsAdapter.getCount());
    }

    public void testGetItem() throws Exception {
        for(int i=0; i<3;i++)
            assertSame(list.get(i), feedItemsAdapter.getItem(i));
    }

    public void testGetItemId() throws Exception {
        for(int i=0; i<3;i++)
            assertEquals(i, feedItemsAdapter.getItemId(i));
    }

    public void testGetView() throws Exception {
        for(int i=0; i<3;i++){
            View view = feedItemsAdapter.getView(i,null,null);
            TextView textView = (TextView) view.findViewById(R.id.feedItemListTitle);
            assertNotNull(view);
            assertNotNull(textView);
            assertEquals(list.get(i).getTitle(), textView.getText());
        }
    }
}