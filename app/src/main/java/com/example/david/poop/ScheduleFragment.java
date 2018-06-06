package com.example.david.poop;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScheduleFragment extends Fragment {

    private WebView myWebView;
    private static final String MY_WEBPAGE = "https://spinitron.com/radio/playlist.php?station=kzsc&show=schedule" +
            "&ptype=d&css=https://www.kzsc.org/wp-content/plugins/kzsc-spinitron/css/spinitron.css";
    private Exception e = null;
    private ProgressBar progressBar;


    private class RetrieveFeedTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
//            Document doc = null;
//            try {
//                doc = Jsoup.connect(MY_WEBPAGE).get();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            Log.d("TITLE", title.toString());
//            Elements scheduleDiv = doc.select("div#bigscheddiv");
//
//            for (Element e : scheduleDiv) {
//
//            }
//            final String html = scheduleDiv.toString();
//            Log.d("TITLE", "before fuck off");
//            Log.d("TITLE", html);
//            Log.d("TITLE", "after fuck off");
//            final String html = doc.getElementById("main").outerHtml();
//            final String mime = "text/html";
//            final String encoding = "utf-8";
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
                    myWebView.getSettings().setLoadWithOverviewMode(true);
                    myWebView.getSettings().setUseWideViewPort(true);
                    myWebView.getSettings().setBuiltInZoomControls(true);
                    myWebView.loadUrl(MY_WEBPAGE);
//                    progressBar.setVisibility(View.GONE);
                    //myWebView.loadData(html, mime, encoding);
                    //myWebView.loadDataWithBaseURL(MY_WEBPAGE, html, mime, encoding, MY_WEBPAGE);
//                    myWebView.setWebViewClient(new WebViewClient());
                    myWebView.setWebViewClient(new WebViewClient() {
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            progressBar.setVisibility(View.GONE);

                        }
                        public boolean shouldOverrideUrlLoading (WebView view, String url){
                            //True if the host application wants to leave the current WebView and handle the url itself, otherwise return false.
                            return true;
                        }
                    });
                }
            });
            return null;
        }
    }
    public RetrieveFeedTask backgroundProcess = null;

    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loadingPanel);
        progressBar.setVisibility(View.VISIBLE);
        myWebView = (WebView) rootView.findViewById(R.id.webView);
        backgroundProcess = new RetrieveFeedTask();
        Object array[] = {rootView};
        backgroundProcess.execute(array);
        return rootView;
    }
}
