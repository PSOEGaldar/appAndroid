package es.psoe.psoegaldar.views.activities;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import junit.framework.TestCase;

import es.psoe.psoegaldar.R;

public class SplashScreenActivityTest extends ActivityInstrumentationTestCase2<SplashScreenActivity> {
    private SplashScreenActivity splashScreenActivity;
    private ImageView imageView;

    public SplashScreenActivityTest() {
        super(SplashScreenActivity.class);
        setName("SplashScreenActivityTest");
    }
    public SplashScreenActivityTest(Class<SplashScreenActivity> activityClass) {
        super(SplashScreenActivity.class);
        setName("SplashScreenActivityTest");
    }

    public void setUp() throws Exception {
        super.setUp();
        splashScreenActivity = getActivity();
        imageView = (ImageView) splashScreenActivity.findViewById(R.id.imageView);
    }

    public void testPreconditions() throws Exception {
        assertNotNull(splashScreenActivity);
        assertNotNull(imageView);
    }
}