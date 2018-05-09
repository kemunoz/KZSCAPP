package com.example.david.poop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 4/20/2018.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private ImageButton btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    private String user_name;
    private String name;
    private DatabaseReference root;
    private String temp_key;

    /*public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = FirebaseDatabase.getInstance().getReference().child("kzsc-3c2de");
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Map<String, Object> map = new HashMap<String, Object>();
                //temp_key = root.push().getKey();
                //root.updateChildren(map);

                DatabaseReference message_root =root.child(temp_key);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", user_name);
                map.put("msg", input_msg.getText().toString());

                message_root.updateChildren(map);
            }
        });
    */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        btn_send_msg = (ImageButton) rootView.findViewById(R.id.imageButton);
        input_msg = (EditText) rootView.findViewById(R.id.input_msg);
        //chat_conversation = (TextView) rootView.findViewById(R.id.chat_conversation);
       root = FirebaseDatabase.getInstance().getReference().child("kzsc-3c2de");
      //btn_send_msg.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              Toast.makeText(getActivity(),"Message", Toast.LENGTH_SHORT).show();
//          }
//      });
//        btn_send_msg.setOnClickListener(this);
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("key value", "blah");
//                Map<String, Object> map = new HashMap<String, Object>();
//                temp_key = root.push().getKey();
//                root.updateChildren(map);

               DatabaseReference message_root =root.child("kzsc-3c2de");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", "kevin");
                map.put("msg", input_msg.getText().toString());

                root.updateChildren(map);

            }
        });


            //Log.d("key value", "blah");

        //request_user_name();
//        btn_send_msg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OnClick(v);
//            }


        //});

       // return inflater.inflate(R.layout.fragment_chat, container, false);
        return rootView;
    }
    public void onClick(View View) {
        //Toast.makeText(getActivity(),"Message", Toast.LENGTH_SHORT).show();
        Log.d("key value", "blah");
        //Map<String, Object> map = new HashMap<String, Object>();
        //temp_key = root.push().getKey();
        //root.updateChildren(map);

        //DatabaseReference message_root =root.child(temp_key);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "kevin");
//        map.put("msg", input_msg.getText().toString());

       // message_root.updateChildren(map);
    }

    private void request_user_name(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Enter name:");
        final EditText input_field = new EditText(this.getActivity());

        builder.setView(input_field);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request_user_name();
            }
        });
        builder.show();
    }

}
