package es.psoe.presenters.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.psoe.models.donwloads.DownloadCalendar;
import es.psoe.models.donwloads.interfaces.DownloadCalendarInterface;
import es.psoe.presenters.CalendarPresenter;

/**
 * Created by exalu_000 on 30/03/2015.
 */
@Module(injects = CalendarPresenter.class, library = true)
public class CalendarPresenterModule {
    DownloadCalendarInterface downloadCalendarInterface;

    public CalendarPresenterModule() {
    }
    public CalendarPresenterModule(DownloadCalendarInterface downloadCalendarInterface) {
        this.downloadCalendarInterface = downloadCalendarInterface;
    }

    @Provides @Singleton public DownloadCalendar provideDownloadCalendar(){
        return new DownloadCalendar(downloadCalendarInterface);
    }
}
