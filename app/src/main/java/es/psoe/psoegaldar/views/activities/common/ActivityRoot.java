package es.psoe.psoegaldar.views.activities.common;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.List;

import dagger.ObjectGraph;
import es.psoe.psoegaldar.App;
import es.psoe.presenters.ObjectGraphInterface;

/**
 * Created by exalu_000 on 03/04/2015.
 */
public abstract class ActivityRoot extends ActionBarActivity implements ObjectGraphInterface{
    protected ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildObjectGraph();
    }

    protected void buildObjectGraph() {
        objectGraph = getObjectGraph().plus(getModules().toArray());
        objectGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        objectGraph = null;
        super.onDestroy();
    }

    protected abstract List<Object> getModules();

    public ObjectGraph getObjectGraph(){
        return ((App) getApplication()).getObjectGraph();
    }
}
