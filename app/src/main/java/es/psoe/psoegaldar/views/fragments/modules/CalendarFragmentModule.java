package es.psoe.psoegaldar.views.fragments.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.psoe.presenters.CalendarPresenter;
import es.psoe.presenters.interfaces.CalendarPresenterInterface;
import es.psoe.psoegaldar.views.fragments.CalendarFragment;

/**
 * Created by exalu_000 on 29/03/2015.
 */
@Module( injects = CalendarFragment.class)
public class CalendarFragmentModule {
    private CalendarPresenterInterface calendarPresenterInterface;

    public CalendarFragmentModule() {}

    public CalendarFragmentModule(CalendarPresenterInterface calendarPresenterInterface) {
        this.calendarPresenterInterface = calendarPresenterInterface;
    }

    @Provides @Singleton public CalendarPresenter provideCalendarPresenter(){
        return new CalendarPresenter(calendarPresenterInterface);
    }
}
