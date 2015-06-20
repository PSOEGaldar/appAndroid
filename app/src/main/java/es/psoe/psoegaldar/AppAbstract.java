package es.psoe.psoegaldar;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by exalu_000 on 05/04/2015.
 */
public abstract class AppAbstract extends Application {
    public static ObjectGraph objectGraph=null;
    @Override public void onCreate() {
        super.onCreate();
    }
    abstract protected List<Object> getModules();
    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }

    public ObjectGraph getObjectGraph(){
        if (null== objectGraph)
            objectGraph = ObjectGraph.create(getModules().toArray());
        return objectGraph;
    }
}