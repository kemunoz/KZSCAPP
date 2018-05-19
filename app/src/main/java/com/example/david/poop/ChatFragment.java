package com.example.david.poop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by David on 4/20/2018.
 */
public class ChatFragment extends Fragment implements RoomListener {
//    private ImageButton btn_send_msg;
//    private EditText input_msg;
//    private TextView chat_conversation;
//    private String user_name;
//    private String name;
//    private DatabaseReference root;
//    private String temp_key;

    private String channelID = "o7RXA1e4aPbMO6AI";
    private String roomName = "observable-room";
    private EditText editText;
    private Scaledrone scaledrone;
    private  MessageAdapter messageAdapter;
    private ListView messagesView;
    private ImageButton sendButton1;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        editText = (EditText) rootView.findViewById(R.id.editText);
        messagesView = (ListView)rootView.findViewById(R.id.messages_view);
        MemberData data = new MemberData(getRandomName(), getRandomColor());
        scaledrone = new Scaledrone(channelID, data);
        sendButton1 = (ImageButton) rootView.findViewById(R.id.sendButton);
        messageAdapter = new MessageAdapter(getActivity().getApplicationContext());
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                Log.d("WTF","Scaledrone connection open");
                // Since the MainActivity itself already implement RoomListener we can pass it as a target
                scaledrone.subscribe(roomName, ChatFragment.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
        sendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                if (message.length() > 0) {
                    Log.d("in if","david");
                    scaledrone.publish("observable-room", message);
                    editText.getText().clear();
                }
            }
        });

        return rootView;


    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Connected to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, final JsonNode json, final Member member) {
            Log.d("inside", "onMEssage!");
;            // To transform the raw JsonNode into a POJO we can use an ObjectMapper
            final ObjectMapper mapper = new ObjectMapper();
            try {
                // member.clientData is a MemberData object, let's parse it as such
                final MemberData data = mapper.treeToValue(member.getClientData(), MemberData.class);
                // if the clientID of the message sender is the same as our's it was sent by us
                boolean belongsToCurrentUser = member.getId().equals(scaledrone.getClientID());
                // since the message body is a simple string in our case we can use json.asText() to parse it as such
                // if it was instead an object we could use a similar pattern to data parsing
                final Message message = new Message(json.asText(), data, belongsToCurrentUser);
//                runOnUiThread (new Runnable() {
//                    @Override
//                    public void run() {
                        messageAdapter.add(message);
                        messagesView.setAdapter(messageAdapter);
                        // scroll the ListView to the last added element
                        messagesView.setSelection(messagesView.getCount() - 1);
//                    }
//                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    // Message.java
    public class Message {
        private String text; // message body
        private MemberData data; // data of the user that sent this message
        private boolean belongsToCurrentUser; // is this message sent by us?

        public Message(String text, MemberData data, boolean belongsToCurrentUser) {
            this.text = text;
            this.data = data;
            this.belongsToCurrentUser = belongsToCurrentUser;
        }

        public String getText() {
            return text;
        }

        public MemberData getData() {
            return data;
        }

        public boolean isBelongsToCurrentUser() {
            return belongsToCurrentUser;
        }
    }


    public class MessageAdapter extends BaseAdapter {

        List<Message> messages = new ArrayList<Message>();
        Context context;

        public MessageAdapter(Context context) {
            this.context = context;
        }

        public void add(Message message) {
            this.messages.add(message);
            notifyDataSetChanged(); // to render the list we need to notify
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int i) {
            return messages.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            MessageViewHolder holder = new MessageViewHolder();
            LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            Message message = messages.get(i);

            if (message.isBelongsToCurrentUser()) { // this message was sent by us so let's create a basic chat bubble on the right
                convertView = messageInflater.inflate(R.layout.my_message, null);
                holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
                convertView.setTag(holder);
                holder.messageBody.setText(message.getText());
            } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
                convertView = messageInflater.inflate(R.layout.their_message, null);
                holder.avatar = (View) convertView.findViewById(R.id.avatar);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
                convertView.setTag(holder);

                holder.name.setText(message.getData().getName());
                holder.messageBody.setText(message.getText());
                GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
                drawable.setColor(Color.parseColor(message.getData().getColor()));
            }

            return convertView;
        }

    }

    class MessageViewHolder {
        public View avatar;
        public TextView name;
        public TextView messageBody;
    }



}
