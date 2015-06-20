package es.psoe.psoegaldar.views.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import es.psoe.presenters.MainPresenter;
import es.psoe.psoegaldar.AppAbstract;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.views.activities.modules.MainActivityModule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.object.HasToString.hasToString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
    MainActivity mainActivity;
    Intent intent;
    Context mContext;
    @Inject MainPresenter mainPresenter;

    @Module(injects = MainActivityTest.class,
            includes = MainActivityModule.class,
            overrides = true)
    public static class MainActivityModuleTestModuleTest{

        @Provides @Singleton public es.psoe.presenters.MainPresenter provideMainPresenter(){
            return mock(MainPresenter.class);
        }
    }

    public MainActivityTest() {
        super(MainActivity.class);
        setName("MainActivity");
    }
    private List<Object> getModules() {
        return Arrays.<Object>asList(new MainActivityModuleTestModuleTest());
    }


    public void setUp() throws Exception {
        super.setUp();
        ObjectGraph objectGraph= ObjectGraph.create(new MainActivityModuleTestModuleTest());
        AppAbstract.objectGraph = objectGraph;
        mContext = new ContextWrapper(getInstrumentation().getTargetContext());
        mContext.setTheme(R.style.Theme_AppCompat);
        mContext.getApplicationContext();
        mainActivity = (MainActivity) launchActivity(mContext.getPackageName(), MainActivity.class, null);
        objectGraph.inject(this);
    }

    public void tearDown() throws Exception {

    }

    public void testPreconditions() throws Exception {
        assertNotNull(mainActivity);
    }

    public void testSelectionNamesCount4() throws Exception {
        int expected = 5;
        int actual = mainActivity.getSectionNames().length;
        assertEquals(expected, actual);
    }

    private String getString(int resId){
        return getInstrumentation().getTargetContext().getString(resId);
    }

    public void testSelectedNoticias() throws Exception {
        //onView(withId(R.id.container)).perform(swipeRight());
        onView(withContentDescription(getString(R.string.navigation_drawer_open))).perform(click());
        onData(hasToString(startsWith("Noticias")))
                .inAdapterView(withId(R.id.drawerListView))
                .perform(click());
        //onView(withId(R.id.drawerListView)).check(matches(isDisplayed()));
        // Es dos veces porque el primer click abre el drawer
        verify(mainPresenter, times(2)).selectedFragment(0);
    }

    public void testSelectedCalendario() throws Exception {
        onView(withContentDescription(getString(R.string.navigation_drawer_open))).perform(click());
        onData(hasToString(startsWith("Calendario")))
                .inAdapterView(withId(R.id.drawerListView))
                .perform(click());
        //onView(withId(R.id.drawerListView)).check(matches(isDisplayed()));
        verify(mainPresenter, times(1)).selectedFragment(1);
    }

    public void testSelectedContacto() throws Exception {
        onView(withContentDescription(getString(R.string.navigation_drawer_open))).perform(click());
        onData(hasToString(startsWith("Contacto")))
                .inAdapterView(withId(R.id.drawerListView))
                .perform(click());
        //onView(withId(R.id.drawerListView)).check(matches(isDisplayed()));
        verify(mainPresenter, times(1)).selectedFragment(2);
    }

    public void testSelectedSendMail() throws Exception {
        onView(withContentDescription(getString(R.string.navigation_drawer_open))).perform(click());
        onData(hasToString(startsWith("Te escuchamos")))
                .inAdapterView(withId(R.id.drawerListView))
                .perform(click());
        //onView(withId(R.id.drawerListView)).check(matches(isDisplayed()));
        verify(mainPresenter, times(1)).selectedFragment(3);
    }
}