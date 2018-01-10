package vn.edu.hust.soict.khacsan.docbaoonline.Model;

/**
 * Created by San on 11/21/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments.CustomFragment;
import vn.edu.hust.soict.khacsan.docbaoonline.Presenter.LoadDataBao;
import vn.edu.hust.soict.khacsan.docbaoonline.View.MainView;


/**
 * Created by San on 11/18/2017.
 */

public class ReadRSS extends AsyncTask<String,Void,ArrayList<TinTuc>> {
    private Context context;
    private ProgressDialog progressBar;
    private int loaiBao, position;
    private MainView mainView;
    public ReadRSS(Context context, int loaiBao,int position,MainView mainView){
        this.context = context;
        this.loaiBao = loaiBao;
        this.position = position;
        this.mainView = mainView;
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
        mainView.LoadTinTuc(tinTucs,loaiBao,position);
        progressBar.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setMessage("Loading...");
        progressBar.show();
    }

}