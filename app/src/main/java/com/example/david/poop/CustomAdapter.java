package com.example.david.poop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends ArrayAdapter<String>{
    CustomAdapter(Context context, List<String> messages){
        super(context, R.layout.custom_row, messages);

    }
    @Override
    public View getView(int position, View  convertView, ViewGroup parent){
        LayoutInflater davidsinflater =LayoutInflater.from(getContext());
        View customView = davidsinflater.inflate(R.layout.custom_row, parent,false);
        String singleMessage = getItem(position);
        TextView textMessage = (TextView) customView.findViewById(R.id.textviewCustom);

        textMessage.setText(singleMessage);
        return  customView;
    }
}
