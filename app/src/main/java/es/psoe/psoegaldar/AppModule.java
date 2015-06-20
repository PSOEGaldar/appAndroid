package es.psoe.psoegaldar;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import es.psoe.psoegaldar.views.activities.modules.MainActivityModule;

/**
 * Created by exalu_000 on 29/03/2015.
 */
@Module(library = true)
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides public Application provideApplication(){
        return app;
    }
}
