package es.psoe.presenters;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import es.psoe.models.donwloads.DownloadCalendar;
import es.psoe.presenters.interfaces.CalendarPresenterInterface;
import es.psoe.presenters.modules.CalendarPresenterModule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalendarPresenterTest {
    ObjectGraph objectGraph;
    CalendarPresenter calendarPresenter;
    @Inject
    DownloadCalendar downloadCalendar;
    CalendarPresenterInterface calendarPresenterInterface;

    @Module(injects = CalendarPresenterTest.class,
            includes = CalendarPresenterModule.class,
            overrides = true)
    static class CalendarPresenterTestModuleTest{
        CalendarPresenterInterface calendarPresenterInterface1;

        CalendarPresenterTestModuleTest(CalendarPresenterInterface calendarPresenterInterface1) {
            this.calendarPresenterInterface1 = calendarPresenterInterface1;
        }

        @Provides
        @Singleton
        public DownloadCalendar provideDownloadCalendar(){
            return mock(DownloadCalendar.class);
        }
    }

    @Before
    public void setUp() throws Exception {
        calendarPresenterInterface = mock(CalendarPresenterInterface.class);
        objectGraph = ObjectGraph.create(new CalendarPresenterTestModuleTest(calendarPresenterInterface));
        when(calendarPresenterInterface.getObjectGraph()).thenReturn(objectGraph);
        when(calendarPresenterInterface.GetURLFeed()).thenReturn("http://www.google.es");
        calendarPresenter = new CalendarPresenter(calendarPresenterInterface);
        objectGraph.inject(this);
    }

    @Test
    public void testIsVisible() throws Exception {
        calendarPresenter.isVisible();
        verify(calendarPresenterInterface,times(1)).isViewVisible();
    }
}