package com.example.david.poop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class CustomAdapter2 extends ArrayAdapter<String> {

    private static int counter = 0;
    private View customView;
    private ImageView album = null;
    private org.json.simple.JSONObject json = null;
    private RetrieveFeedTask backgroundProcess;
    private static String url = "";
    private static Object array[];
    private Bitmap bmp = null;

    CustomAdapter2(Context context, List<String> messages) {
        super(context, R.layout.custom_row2, messages);
        Log.d("FUCK", messages.toString());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getContext() != null) {
            LayoutInflater davidsinflater = LayoutInflater.from(getContext());
            String jsonString = getItem(position);
            JSONParser parser = new JSONParser();
            try {
                json = (org.json.simple.JSONObject) parser.parse(jsonString);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            customView = davidsinflater.inflate(R.layout.custom_row2, parent, false);
            album = (ImageView) customView.findViewById(R.id.album);
            TextView artistName = (TextView) customView.findViewById(R.id.artist);
            TextView songName = (TextView) customView.findViewById(R.id.song);
            artistName.setText(json.get("song").toString());
            songName.setText(json.get("artist").toString());
            backgroundProcess = new RetrieveFeedTask();
            url = json.get("url").toString();
            if (!url.equals("None")) {
                array = new Object[1];
                array[0] = url;
                Log.d("LOL", url);
                Log.d("LOL2", array[0].toString());
                backgroundProcess.execute(array);
            }
            //provide default album cover if none is provided!! :D
            else {
                album.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.noalbum));
            }
            return customView;
        }
        return null;
    }

    private class RetrieveFeedTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            Log.d("LOL3", objects[0].toString());
            URL url = null;
            try {
                Log.d("LOL4", objects[0].toString());
                url = new URL(objects[0].toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            album.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("HERE", "h");
                    album.setImageBitmap(bmp);
            }
        });
        return null;
        }
    }
}