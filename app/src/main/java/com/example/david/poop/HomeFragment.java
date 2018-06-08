package com.example.david.poop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by David on 4/20/2018.
 */

public class HomeFragment extends Fragment {

    //    single-product-summary
    private WebView myWebView;
    private String MY_WEBPAGE = "https://spinitron.com/radio/playlist.php?station=kzsc#main";
    private Exception e = null;
    private ProgressBar progressBar;
    private static ArrayList<String> artists = new ArrayList<String>();
    private static ArrayList<String> urls = new ArrayList<String>();
    private ListView mListview;
    private Document doc;

    private class RetrieveFeedTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                doc = Jsoup.connect(MY_WEBPAGE).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            artists.clear();
            String artist = "";
            String song = "";
            String url = "";
            Elements elements = doc.getElementsByClass("nfo");
            Elements imageElements = doc.getElementsByClass("art");

            for (Element element : imageElements) {
                  Elements imageElement = element.select("img");
//                element.select("img").absUrl("src");
//                String absoluteUrl = imageElement.absUrl("src");  //absolute URL on src
                String srcValue = imageElement.attr("src");  // exact content value of the attribute.
                urls.add(srcValue);
                Log.d("URL:", srcValue);
            }

            int counter = 0;
            for (Element element : elements) {
                Log.d("ELE", element.toString());
                JSONObject J = new JSONObject();
                Elements els1 = element.getElementsByClass("aw");
                Elements els2 = element.getElementsByClass("sn");
//                Elements els3 = element.select("img");
//                element.select("img").absUrl("src");
//                String absoluteUrl = imageElement.absUrl("src");  //absolute URL on src
//                String srcValue = imageElement.attr("src");  // exact content value of the attribute.
//                Log.d("URL:", srcValue);
                artist = els1.select("a").text().toString();
                Log.d("ARTISTS:", artist.toString());
                song = els2.text();
                    try {
                        J.put("artist", artist);
                        J.put("song", song);

                        //sometimes no image is provided
                        if (urls.size() > 0 ) {
                            if (urls.get(counter).equals("")) {
                                J.put("url", "None");
                            }
                            else
                                J.put("url", urls.get(counter));
                        }
                        else {
                            J.put("url", "None");
                        }
                        artists.add(J.toString());
                        counter++;
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListAdapter listAd = new CustomAdapter2(getContext(), artists);
                    mListview.setAdapter(listAd);
                }
            });
            return null;
        }
}
    private RetrieveFeedTask backgroundProcess = null;

    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mListview = (ListView) rootView.findViewById(R.id.listview);
        backgroundProcess = new RetrieveFeedTask();
        Object array[] = {rootView};
        backgroundProcess.execute(array);
        return rootView;
    }

}
