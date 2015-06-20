package es.psoe.psoegaldar.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.psoe.models.items.FeedItem;
import es.psoe.psoegaldar.R;

import static android.view.LayoutInflater.from;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class FeedItemsAdapter extends BaseAdapter {
    private final Context context;
    private final List<FeedItem> feedItems;
    private final Typeface fontTitle;


    public FeedItemsAdapter(Context context, List<FeedItem> feedItems) {
        this.context = context;
        this.feedItems = feedItems;
        this.fontTitle = Typeface.createFromAsset(context.getAssets(), "fonts/INR____.TTF");
    }

    @Override
    public int getCount() {
        return this.feedItems.size();
    }

    @Override
    public java.lang.Object getItem(int position) {
        return this.feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = from(context);
        convertView = inflater.inflate( R.layout.layout_feed_item_list, null );
        TextView textView = (TextView) convertView.findViewById(R.id.feedItemListTitle);
        textView.setText(feedItems.get(position).getTitle());
        //textView.setTypeface(fontTitle);
        return convertView;
    }
}