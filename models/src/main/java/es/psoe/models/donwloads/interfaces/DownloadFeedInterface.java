package es.psoe.models.donwloads.interfaces;

import java.util.List;

import es.psoe.models.items.FeedItem;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public interface DownloadFeedInterface {
    void processFinish(List<FeedItem> output);
    Boolean isVisible();
}
