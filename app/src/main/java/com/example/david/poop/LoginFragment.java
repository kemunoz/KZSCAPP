package com.example.david.poop;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginFragment extends Fragment {

    private CoordinatorLayout mLoginLayout;
    private EditText prompt, mUserNicknameEditText;
    private Button mConnectButton;
    private ContentLoadingProgressBar mProgressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        super.onCreate(savedInstanceState);
        mLoginLayout = (CoordinatorLayout) rootView.findViewById(R.id.layout_login);
        mUserNicknameEditText = (EditText) rootView.findViewById(R.id.edittext_login_user_nickname);
        mUserNicknameEditText.setSelectAllOnFocus(true);
        mProgressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.progress_bar_login);
        prompt = (EditText) rootView.findViewById(R.id.promptUser);
        prompt.setEnabled(false);

        mConnectButton = (Button) rootView.findViewById(R.id.button_login_connect);
        mConnectButton.setBackgroundColor(Color.BLACK);
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNickname = mUserNicknameEditText.getText().toString();
                LoginFragment login = new LoginFragment();

                if (userNickname.equals("")) {
                    Toast.makeText(getActivity(),"invalid username!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            login).commit();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("nickname", userNickname);
                    ChatFragment chat = new ChatFragment();
                    chat.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            chat).commit();
                }

            }
        });

        return rootView;
    }

    private void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show();
        } else {
            mProgressBar.hide();
        }
    }


}
