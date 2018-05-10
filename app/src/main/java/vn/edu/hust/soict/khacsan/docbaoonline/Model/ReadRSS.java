package vn.edu.hust.soict.khacsan.docbaoonline.Model;

/**
 * Created by San on 11/21/2017.
 */

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by San on 11/18/2017.
 */

public class ReadRSS extends AsyncTask<String,Void,ArrayList<TinTuc>> {
    private getNewsCallback onPostExecute;
    public ReadRSS(getNewsCallback onPostExecute){
        this.onPostExecute = onPostExecute;
    }


    @Override
    protected ArrayList<TinTuc> doInBackground(String... strings) {
        ArrayList<TinTuc> list = new ArrayList<>();
        try {
//            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
//            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

            Document document = Jsoup.connect(strings[0]).get();
            Elements elements = document.select("item");
            for (Element element :
                    elements) {
                String title = element.select("title").text();
                String link = element.select("link").text();
                String date = element.select("pubDate").text();
                String descriptionTag = element.select("description").text();
                //String description =descriptionTag.substring(descriptionTag.indexOf("</br>")+5);
                //HTML => dung Jsoup -> parse tiếp -> img
                Document documentImg = Jsoup.parse(descriptionTag);
                String urlImage = documentImg.select("img").attr("src");
                list.add(new TinTuc(title,urlImage,date,"",link));
            }
        } catch (IOException e) {
            e.printStackTrace();
       }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<TinTuc> tinTucs) {
        super.onPostExecute(tinTucs);
        onPostExecute.execute(tinTucs);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface getNewsCallback{
        void  execute(ArrayList<TinTuc> tinTucs);
    }

}