package es.psoe.presenters.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.psoe.models.donwloads.DownloadFeed;
import es.psoe.models.donwloads.interfaces.DownloadFeedInterface;
import es.psoe.presenters.NewsFeedPresenter;

/**
 * Created by exalu_000 on 30/03/2015.
 */
@Module(injects = NewsFeedPresenter.class)
public class NewsFeedPresenterModule {
    DownloadFeedInterface downloadFeedInterface;

    public NewsFeedPresenterModule(DownloadFeedInterface downloadFeedInterface) {
        this.downloadFeedInterface = downloadFeedInterface;
    }

    @Provides @Singleton public DownloadFeed provideDownloadFeed(){
        return new DownloadFeed(downloadFeedInterface);
    }
}
