package es.psoe.presenters;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

import es.psoe.presenters.interfaces.MainPresenterInterface;

public class MainPresenterTest extends TestCase {

    MainPresenterInterface mainActivityFake;
    MainPresenter mainPresenter;

    public void setUp() throws Exception {
        super.setUp();
        mainActivityFake = mock(MainPresenterInterface.class);
        when(mainActivityFake.getSectionNames())
                .thenReturn(new String[]{"Primero", "Segundo"});

        mainPresenter = new MainPresenter(mainActivityFake);
    }

    public void tearDown() throws Exception {}

    public void testSelectedFragment() throws Exception {
        for(int i=0;i<3;i++)
            mainPresenter.selectedFragment(i);

        verify(mainActivityFake, times(1)).getNewsFeedFragment();
        verify(mainActivityFake, times(1)).getCalendarFragment();
        verify(mainActivityFake, times(1)).getWhereAreFragment();
        verify(mainActivityFake, times(3)).getFragment(null);

        mainPresenter.selectedFragment(3);
        verify(mainActivityFake, times(1)).sendMail();
    }

    public void testSectionAttached() throws Exception {
        final String actual = mainPresenter.sectionAttached(1);
        final String expected = "Primero";

        assertEquals(expected, actual);
    }
}