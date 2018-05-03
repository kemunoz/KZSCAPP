package com.example.david.poop;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.Button;
/**
 * Created by David on 4/20/2018.
 */

public class HomeFragment extends Fragment {
    ImageButton playButton;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_home,container, false);

        playButton = (ImageButton)v.findViewById(R.id.playButton);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        final String AudioURL = "http://188.165.192.5:8242/kzschigh?type=.mp3";

        //Preparing audio
        try {
            mediaPlayer.setDataSource(AudioURL);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(RuntimeException e) {
            e.printStackTrace();
        }

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //mediaPlayer.start();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    //playButton.setText("Play");
                    playButton.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.pause);
                    //playButton.setText("Pause");
                }
            }
        });

        return v;
    }


}
