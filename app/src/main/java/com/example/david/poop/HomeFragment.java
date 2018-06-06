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
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by David on 4/20/2018.
 */

public class HomeFragment extends Fragment {

    //    single-product-summary
    private WebView myWebView;
    private String MY_WEBPAGE = "https://spinitron.com/radio/playlist.php?station=kzsc#main";
    private Exception e = null;
    private ProgressBar progressBar;


    private class RetrieveFeedTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            Document doc = null;
            try {
                doc = Jsoup.connect(MY_WEBPAGE).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Elements els = doc.getElementsByClass("plblock withart withbuy");
            final String html = els.toString();
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
                    myWebView.getSettings().setLoadWithOverviewMode(true);
                    myWebView.getSettings().setUseWideViewPort(true);
                    myWebView.getSettings().setBuiltInZoomControls(true);
//                    myWebView.loadUrl(MY_WEBPAGE);
//                    progressBar.setVisibility(View.GONE);
                    myWebView.loadData(html, mime, encoding);
//                    myWebView.loadDataWithBaseURL(MY_WEBPAGE, html, mime, encoding, MY_WEBPAGE);
//                    myWebView.setWebViewClient(new WebViewClient());
                    myWebView.setWebViewClient(new WebViewClient() {
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
            return null;
        }
    }
    private RetrieveFeedTask backgroundProcess = null;

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
