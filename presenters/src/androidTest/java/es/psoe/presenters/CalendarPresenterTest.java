package es.psoe.presenters;

import junit.framework.TestCase;

import java.net.URL;

import static org.mockito.Mockito.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import es.psoe.models.donwloads.DownloadCalendar;
import es.psoe.models.donwloads.interfaces.DownloadCalendarInterface;
import es.psoe.presenters.interfaces.CalendarPresenterInterface;
import es.psoe.presenters.modules.CalendarPresenterModule;

public class CalendarPresenterTest extends TestCase {
    ObjectGraph objectGraph;
    CalendarPresenter calendarPresenter;
    @Inject DownloadCalendar downloadCalendar;
    CalendarPresenterInterface calendarPresenterInterface;

    @Module(injects = CalendarPresenterTest.class,
            includes = CalendarPresenterModule.class,
            overrides = true)
    static class CalendarPresenterTestModuleTest{
        CalendarPresenterInterface calendarPresenterInterface1;

        CalendarPresenterTestModuleTest(CalendarPresenterInterface calendarPresenterInterface1) {
            this.calendarPresenterInterface1 = calendarPresenterInterface1;
        }

        @Provides @Singleton public DownloadCalendar provideDownloadCalendar(){
            return mock(DownloadCalendar.class);
        }
    }

    public void setUp() throws Exception {
        super.setUp();
        calendarPresenterInterface = mock(CalendarPresenterInterface.class);
        objectGraph = ObjectGraph.create(new CalendarPresenterTestModuleTest(calendarPresenterInterface));
        when(calendarPresenterInterface.getObjectGraph()).thenReturn(objectGraph);
        when(calendarPresenterInterface.GetURLFeed()).thenReturn("http://www.google.es");
        calendarPresenter = new CalendarPresenter(calendarPresenterInterface);
        objectGraph.inject(this);
    }

    public void tearDown() throws Exception {

    }

    public void testProcessFinish() throws Exception {
    }

    public void testIsVisible() throws Exception {
        calendarPresenter.isVisible();
        verify(calendarPresenterInterface,times(1)).isViewVisible();
    }

    public void testGetCalendar() throws Exception {
        /*when(calendarPresenterInterface.isNetwork()).thenReturn(true);
        calendarPresenter.GetCalendar();
        verify(downloadCalendar, times(1)).execute(any(URL.class));*/
        fail("Hay que configurar poewrMock para probarlo ya que execute es un método final");
    }
}