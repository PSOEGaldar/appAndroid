package es.psoe.psoegaldar.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import es.psoe.models.items.FeedItem;
import es.psoe.presenters.NewsFeedPresenter;
import es.psoe.presenters.interfaces.NewsFeedPresenterInterface;
import es.psoe.psoegaldar.R;
import es.psoe.psoegaldar.helpers.FeedItemsAdapter;
import es.psoe.psoegaldar.views.activities.FeedActivity;
import es.psoe.psoegaldar.views.activities.MainActivity;
import es.psoe.psoegaldar.views.fragments.common.FragmentRoot;
import es.psoe.psoegaldar.views.fragments.modules.NewsFeedFragmentModule;


public class NewsFeedFragment extends FragmentRoot implements NewsFeedPresenterInterface {
    @Inject NewsFeedPresenter feedsPresenter;
    private ListView listView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFeeds = inflater.inflate(R.layout.fragment_news_feed, container, false);
        return listFeeds;
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView) (getActivity().findViewById(R.id.listView));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("es.psoe.psoegaldar.views.activities.FeedActivity");
                Bundle bundle = new Bundle();
                bundle.putString("feedSummary", feedsPresenter.getSummaryFeed(position));
                bundle.putString("datePost", feedsPresenter.getDatePost(position));
                bundle.putString("feedTitle", feedsPresenter.getTitleFeed(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        feedsPresenter.GetFeed();
    }


    @Override
    public String GetURLFeed() {
        return getString(R.string.url_feed);
    }

    @Override
    public void ReadUpdates(List<FeedItem> feed) {
        ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        TextView textView = (TextView) getActivity().findViewById(R.id.no_network);
        textView.setVisibility(View.INVISIBLE);
        if (feed.size() != 0) {
            FeedItemsAdapter feedItemsAdapter = new FeedItemsAdapter(getActivity().getApplicationContext(), feed);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(feedItemsAdapter);
        } else {
            TextView textView1 = (TextView) getActivity().findViewById(R.id.no_new_feeds);
            textView1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getBaseContext() {
        return getActivity().getBaseContext();
    }

    @Override
    public Boolean isViewVisible() {
        return isVisible();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new NewsFeedFragmentModule(this));
    }
}
