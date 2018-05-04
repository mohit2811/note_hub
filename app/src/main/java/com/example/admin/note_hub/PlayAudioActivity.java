package com.example.admin.note_hub;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class PlayAudioActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mMediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);

        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fetchAudioUrlFromFirebase();
    }

    private void fetchAudioUrlFromFirebase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("audio_urls").child(getIntent().getStringExtra("images_key")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Upload audio_url = dataSnapshot.getValue(Upload.class);


                try {
                    mMediaplayer.setDataSource(audio_url.getUrl());
                    // wait for media player to get prepare
                    mMediaplayer.setOnPreparedListener(PlayAudioActivity.this);
                    mMediaplayer.prepareAsync();

                } catch (IOException e) {
                    e.printStackTrace();
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
