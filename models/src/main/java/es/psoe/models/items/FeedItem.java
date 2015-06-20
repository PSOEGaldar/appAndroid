package es.psoe.models.items;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by exalu_000 on 29/03/2015.
 */
public class FeedItem {
    private String title;
    private String description;
    private Date date = null;

    public Date getDate() {
        return date;
    }

    public void setStringDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            this.date = dateFormat.parse(date.substring(5, 25));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return (new CleanHTML()).clean(this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private class CleanHTML{

        public String clean(String source) {
            boolean isTagAttributes = false;
            StringBuilder result = new StringBuilder();
            StringBuffer tags = new StringBuffer();
            String html=source;
            Pattern pattern = Pattern.compile("<p.style=\"text-align:center;\">(.*)height=\"1\"./>");
            Matcher matcher = pattern.matcher(html);
            html=matcher.replaceAll("");

            for(int i=0;i<html.length();i++){
                char character = html.charAt(i);
                if('<' == character){
                    isTagAttributes = true;
                }
                if(!isTagAttributes){
                    result.append(character);
                }else if('<' != character && '>' != character){
                    tags.append(character);
                }
                if('>' == character){
                    isTagAttributes = false;
                    result.append(tagHTMLClean(tags.toString()));
                    tags=new StringBuffer();
                }
            }
            return result.toString();
        }

        private String tagHTMLClean(String source) {
            StringBuilder result = new StringBuilder();
            for(int i=0; i<source.length();i++)
            {
                char  character = source.charAt(i);
                if(character==' '){
                    result.append(hrefSrcValue(source.substring(i)));
                    break;
                }
                result.append(character);
            }
            return "<" + result.toString() + ">";
        }

        private String hrefSrcValue(String source){
            StringBuilder result = new StringBuilder();
            if(source.contains("href=")){

                String data = source.substring(source.indexOf("href=")+6);
                char limit = source.substring(source.indexOf("href=")).charAt(5);

                StringBuilder href = new StringBuilder(" href="+limit);
                for(int i=0; i<data.length();i++){
                    char character = data.charAt(i);
                    if(character == limit){
                        href.append(limit);
                        break;
                    }
                    href.append(character);
                }
                result.append(href);
            }
            if(source.contains("src=")){

                String data = source.substring(source.indexOf("src=")+5);
                char limit = source.substring(source.indexOf("src=")).charAt(4);

                StringBuilder href = new StringBuilder(" src="+limit);
                for(int i=0; i<data.length();i++){
                    char character = data.charAt(i);
                    if(character == limit){
                        href.append(limit);
                        break;
                    }
                    href.append(character);
                }
                result.append(href);
            }
            return result.toString();
        }
    }
}