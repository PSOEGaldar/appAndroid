package es.psoe.models.donwloads;

import android.os.AsyncTask;
//import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.psoe.models.donwloads.interfaces.DownloadFeedInterface;
import es.psoe.models.items.FeedItem;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class DownloadFeed extends AsyncTask<URL,Integer,List<FeedItem>> {
    private final WeakReference<DownloadFeedInterface> delegate;


    public DownloadFeed(DownloadFeedInterface d){
        super();
        delegate = new WeakReference<>(d);
    }

    @Override
    protected List<FeedItem> doInBackground(URL... params) {
        //String result="";
        List<FeedItem> result = null;
        try {
            result = getFeedString(getSFeedStream(params[0]));

        } catch (Exception e){
            //Log.w("PSOEGaldar", "Error en la conexiÃ³n http");
        }
        return result;
    }

    private List<FeedItem> getFeedString(InputStream stream)
            throws IOException, XmlPullParserException
    {
        return (new GetFeedString(stream)).invoke();
    }

    @Override
    protected void onPostExecute(List<FeedItem> items) {
        DownloadFeedInterface fDelegate = delegate.get();
        if(fDelegate!=null && fDelegate.isVisible()) {
            super.onPostExecute(items);
            fDelegate.processFinish(items);
        }
    }

    private InputStream getSFeedStream(URL param){

        InputStream result = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) param.openConnection();
            result = new BufferedInputStream(conn.getInputStream());
        }catch (Exception e){
            //Log.w("PSOEGaldar", "Error en la conexiÃ³n http");
        }

        return  result;
    }

    private class GetFeedString {
        private final List<FeedItem> result;
        private final XmlPullParserFactory factory;
        private XmlPullParser xpp;
        private int event;
        private String text;
        private FeedItem item;
        private List<String> imagesItem;
        private String name;

        public GetFeedString(InputStream stream) throws XmlPullParserException {
            result = new ArrayList<>();

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            xpp = factory.newPullParser();
            xpp.setInput(stream, null);
        }

        public List<FeedItem> invoke() throws XmlPullParserException, IOException {
            event = xpp.getEventType();
            text = null;
            item = null;
            imagesItem = null;

            extractListFeedItems();
            return result;
        }

        private void extractListFeedItems() throws XmlPullParserException, IOException {
            while (event != XmlPullParser.END_DOCUMENT){
                name = xpp.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        xppStartTag();
                        break;
                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        xppEndTag();
                        break;
                }
                event = xpp.next();
            }
        }

        private void xppEndTag() {
            if(null!= item) {
                if (name.equals("title")&& xpp.getPrefix()==null) {
                    item.setTitle(text);
                } else if (name.equals("encoded")) {
                    item.setDescription(text);
                } else if (name.equals("item")) {
                    result.add(item);
                    item = null;
                } else if (name.equals("pubDate")) {
                    item.setStringDate(text);
                }
            }
        }

        private void xppStartTag() {
            if(name.equals("item"))
                item = new FeedItem();
            if(name.equals("content")&& xpp.getPrefix().equals("media")) {
                Map<String,String> attrs = new HashMap<>();
                for(int x=0;x< xpp.getAttributeCount();x++) {
                    attrs.put(xpp.getAttributeName(x), xpp.getAttributeValue(x));
                }
                if(attrs.get("medium").equals("image")){
                    if(null== imagesItem)
                        imagesItem = new ArrayList<>();
                    imagesItem.add(attrs.get("url"));
                }
            }
        }
    }
}