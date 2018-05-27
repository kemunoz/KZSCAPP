package com.example.david.poop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.simple.parser.JSONParser;

import java.util.List;

class CustomAdapter extends ArrayAdapter<String>{

    CustomAdapter(Context context, List<String> messages){
        super(context, R.layout.custom_row, messages);
    }
    @Override
    public View getView(int position, View  convertView, ViewGroup parent){
        LayoutInflater davidsinflater =LayoutInflater.from(getContext());
        String jsonString = getItem(position);
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject json = null;
        try {
            json = (org.json.simple.JSONObject) parser.parse(jsonString);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        String isDJ = json.get("isDJ").toString();

        View customView;
        if (isDJ == "false") {
            customView = davidsinflater.inflate(R.layout.custom_row, parent,false);
            TextView textMessage = (TextView) customView.findViewById(R.id.textviewCustom);
            TextView nameMessage = (TextView) customView.findViewById(R.id.textName);
            textMessage.setText(json.get("msg").toString());
            nameMessage.setText("From  " + json.get("name").toString() + ": ");
        }
        //The particular entry is from the DJ
        else {
             customView = davidsinflater.inflate(R.layout.custom_row_dj, parent,false);
            TextView textMessage = (TextView) customView.findViewById(R.id.textviewCustom);
            TextView nameMessage = (TextView) customView.findViewById(R.id.textName);
            textMessage.setText(json.get("msg").toString());
            nameMessage.setText("From  " + json.get("name").toString() + ": ");
        }
        return  customView;
    }
}
