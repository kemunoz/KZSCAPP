package com.example.david.poop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 4/20/2018.
 */
public class ChatFragment extends Fragment {
    private ImageButton btn_send_msg;
    private EditText input_msg;
    private ListView chat_conversation;
    private String user_name;
    private String name = "";
    private DatabaseReference root;
    private String temp_key;
    private float n = 0;
    private int l =0;
    private String DJSecretKey = "DJ";
    private String isDJ = "False";
    private Context mContext;
    private Activity a;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        name = getArguments().getString("nickname");
        //Toast.makeText(getActivity(),"name is " + name, Toast.LENGTH_LONG).show();
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        btn_send_msg = (ImageButton) rootView.findViewById(R.id.imageButton);
        input_msg = (EditText) rootView.findViewById(R.id.input_msg);
        chat_conversation = (ListView) rootView.findViewById(R.id.chat_conversation);
       root = FirebaseDatabase.getInstance().getReference().child("kzsc-3c2de");
       //request_user_name();

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

               DatabaseReference message_root =root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", name);
                map2.put("msg", input_msg.getText().toString());
                message_root.updateChildren(map2);
                input_msg.setText(""); //clear chatbox after a sent message
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> lol = new ArrayList<String>();
                JSONObject J = new JSONObject();
                for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
//                    Log.d("ITEM", item_snapshot.child("msg").getValue().toString());
                    String nameForJSON = item_snapshot.child("name").getValue().toString();
                    if (nameForJSON.equals(DJSecretKey)) {
                        isDJ = "True";
                    }
                    else {
                        isDJ = "False";
                    }
                    String messageForJSON = item_snapshot.child("msg").getValue().toString();
                    try {
                        J.put("name", nameForJSON);
                        J.put("msg", messageForJSON);
                        J.put("isDJ", isDJ);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    lol.add(J.toString());
                }
                if (mContext != null) {
                    ListAdapter listAd = new CustomAdapter(mContext, lol);
                    chat_conversation.setAdapter(listAd);
                    chat_conversation.setSelection(lol.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity){
            a = (Activity) context;
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
