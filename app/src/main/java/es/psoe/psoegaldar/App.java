package es.psoe.psoegaldar;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by exalu_000 on 28/03/2015.
 */
public class App extends AppAbstract {
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }
}
