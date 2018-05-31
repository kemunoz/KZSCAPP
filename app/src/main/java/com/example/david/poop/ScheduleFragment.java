package com.example.david.poop;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScheduleFragment extends Fragment {

    private WebView myWebView;
    private static final String MY_WEBPAGE = "https://www.kzsc.org/schedule";
    private Exception e = null;


    private class RetrieveFeedTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            Document doc = null;
            try {
                doc = Jsoup.connect(MY_WEBPAGE).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            final Element title = doc.body();
            Log.d("TITLE", title.toString());
            //Elements ele = doc.select("div#middle");
            //final String html = ele.toString();
            //Log.d("TITLE", "before fuck off");
//            Log.d("TITLE", html);
            Log.d("TITLE", "after fuck off");
            final String mime = "text/html";
            final String encoding = "utf-8";
            myWebView.post(new Runnable() {
                @Override
                public void run() {
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.setFocusable(true);
                    myWebView.setFocusableInTouchMode(true);
                    myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                    myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    myWebView.getSettings().setDatabaseEnabled(true);
                    myWebView.getSettings().setAppCacheEnabled(true);
                    myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    //myWebView.loadUrl(MY_WEBPAGE);
                    //myWebView.loadData(html, mime, encoding);
                    myWebView.loadDataWithBaseURL(MY_WEBPAGE, title.toString(), mime, encoding, MY_WEBPAGE);
                    myWebView.setWebViewClient(new WebViewClient());
                }
            });
            return null;
        }
    }
    private RetrieveFeedTask backgroundProcess = null;

    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        myWebView = (WebView) rootView.findViewById(R.id.webView);
        backgroundProcess = new RetrieveFeedTask();
        Object array[] = {rootView};
        backgroundProcess.execute(array);
        return rootView;
    }
}
