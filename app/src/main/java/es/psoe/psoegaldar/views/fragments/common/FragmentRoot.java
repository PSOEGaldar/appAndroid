package es.psoe.psoegaldar.views.fragments.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dagger.ObjectGraph;
import es.psoe.presenters.ObjectGraphInterface;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.views.activities.common.ActivityRoot;

/**
 * Created by exalu_000 on 03/04/2015.
 */
public abstract class FragmentRoot  extends Fragment implements ObjectGraphInterface {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objectGraph = getObjectGraph().plus(getModules().toArray());
        objectGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        objectGraph = null;
        super.onDestroy();
    }

    public boolean isNetwork(){
        boolean result = false;
        ConnectivityManager con = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = con.getAllNetworkInfo();
        for (int i = 0; i < 2; i++)
            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                result = true;
        return result;
    }

    public void setNoNetwork() {
        ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        TextView textView = (TextView) getActivity().findViewById(R.id.no_network);
        textView.setVisibility(View.VISIBLE);

        Toast.makeText(getActivity().getBaseContext(),
                "Hay problemas con la conexión a Internet.", Toast.LENGTH_SHORT)
                .show();
    }

    public ObjectGraph getObjectGraph(){
        return ((ObjectGraphInterface)getActivity()).getObjectGraph();
    }

    protected abstract List<Object> getModules();
}