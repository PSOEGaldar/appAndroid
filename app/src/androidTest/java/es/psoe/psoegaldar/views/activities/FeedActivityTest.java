package es.psoe.psoegaldar.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;

import es.psoe.psoegaldar.R;

public class FeedActivityTest extends ActivityUnitTestCase<FeedActivity> {
    final String summary ="<p>newss body.</p>\n";
    final String datePost ="Sábado, 1-3-1111";
    final String title ="test FeedActivity";
    private FeedActivity feedActivity;
    private WebView webView;

    public FeedActivityTest() {
        super(FeedActivity.class);
        setName("FeedActivity");
    }

    public void setUp() throws Exception {
        super.setUp();

        Context context = getInstrumentation().getTargetContext();
        context.setTheme(R.style.Theme_AppCompat);
        Bundle bundle = new Bundle();
        bundle.putString("feedSummary", summary);
        bundle.putString("datePost", datePost);
        bundle.putString("feedTitle", title);
        feedActivity = (FeedActivity) launchActivity(context.getPackageName(), FeedActivity.class, bundle);
        //getInstrumentation().waitForIdleSync();
        webView = (WebView) feedActivity.findViewById(R.id.feed_webview);
    }

    @Override
    public void tearDown() throws Exception {
        feedActivity.finish();
        feedActivity = null;
        super.tearDown();
    }

    public void testPreconditions() throws Exception {
        assertNotNull(feedActivity);
        assertNotNull(webView);
    }

    public void testShouldFinish() throws Exception {
        sendKeys(KeyEvent.KEYCODE_BACK);
        assertTrue(feedActivity.isFinishing());
    }

    public void testWebViewCoverEntireScreen() throws Exception {
        final int expected = ViewGroup.LayoutParams.MATCH_PARENT;
        final ViewGroup.LayoutParams lp = webView.getLayoutParams();

        assertEquals(expected, lp.width);
        assertEquals(expected, lp.height);
    }
}