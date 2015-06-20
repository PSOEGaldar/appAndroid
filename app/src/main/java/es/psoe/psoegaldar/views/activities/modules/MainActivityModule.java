package es.psoe.psoegaldar.views.activities.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.psoe.presenters.interfaces.MainPresenterInterface;
import es.psoe.psoegaldar.views.activities.MainActivity;

/**
 * Created by exalu_000 on 29/03/2015.
 */
@Module(
        injects = MainActivity.class
)
public class MainActivityModule {
    MainPresenterInterface activity;

    public MainActivityModule() {

    }
    public MainActivityModule(MainPresenterInterface app) {
        this.activity = app;
    }

    @Provides @Singleton
    public es.psoe.presenters.MainPresenter provideMainPresenter(){
        return new es.psoe.presenters.MainPresenter(activity);
    }
}
