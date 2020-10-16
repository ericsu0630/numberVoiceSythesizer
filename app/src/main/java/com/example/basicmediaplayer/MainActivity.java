package com.example.basicmediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer voicePlayerTimer;
    MediaPlayer voiceMediaPlayer;
    ArrayList<Integer> voicePlaylist;
    int voicePlaylistCounter =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlayListBuilder playlist = new PlayListBuilder();
        voicePlaylist = playlist.buildPlaylist(999, this);
        voiceMediaPlayer = MediaPlayer.create(this,voicePlaylist.get(0));
        voiceMediaPlayer.start();
        voicePlayerTimer = new Timer();
        if (voicePlaylist.size()>1) playNext();
    }

    public void playNext() {
        voicePlayerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                voiceMediaPlayer.reset();
                voiceMediaPlayer = MediaPlayer.create(MainActivity.this,voicePlaylist.get(++voicePlaylistCounter));
                voiceMediaPlayer.start();
                if (voicePlaylist.size() > voicePlaylistCounter +1) {
                    playNext();
                }
            }
        }, voiceMediaPlayer.getDuration());
    }

    @Override
    public void onDestroy() {
        if (voiceMediaPlayer.isPlaying())
            voiceMediaPlayer.stop();
            voiceMediaPlayer.release();
        voicePlayerTimer.cancel();
        super.onDestroy();
    }
}