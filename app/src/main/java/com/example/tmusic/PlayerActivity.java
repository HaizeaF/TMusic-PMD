package com.example.tmusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private ImageButton buttonStartPause;
    private ImageButton buttonNextSong;
    private ImageButton buttonPreviousSong;
    private ImageButton buttonLoop;
    private ImageButton buttonBack;
    private SeekBar seekBarMusic;
    private TextView textElapsedTime;
    private TextView textTimeLeft;
    private Integer totalTime;
    private MediaPlayer mediaPlayer;
    private VideoView videoSong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //Video
        videoSong = findViewById(R.id.videoSong);

        //Uri path = Uri.parse("android.resource://" + getPackageName() + "/" R.raw.video1); //do not add any extension
        //if your file is named sherif.mp4 and placed in /raw
        //use R.raw.sherif

        //videoSong.setVideoURI(path);
        videoSong.start();

        //Music Controller
        buttonStartPause = findViewById(R.id.buttonStartPause);
        buttonLoop = findViewById(R.id.buttonLoop);
        textElapsedTime = findViewById(R.id.textElapsedTime);
        textTimeLeft = findViewById(R.id.textTimeLeft);

        //mediaPlayer = MediaPlayer.create(this, R.raw.video1);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        mediaPlayer.setVolume(0.5f,0.5f);
        totalTime = mediaPlayer.getDuration();

        seekBarMusic = findViewById(R.id.seekBarMusic);
        seekBarMusic.setMax(totalTime);

        /*
        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying()){
                    buttonStartPause.setBackgroundResource(R.drawable.ic_music_pause);
                    mediaPlayer.start();
                }else{
                    buttonStartPause.setBackgroundResource(R.drawable.ic_music_play);
                    mediaPlayer.pause();
                }
            }
        });


        buttonLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isLooping()){
                    buttonLoop.setBackgroundResource(R.drawable.music_loop_button_background_true);
                    buttonLoop.setImageResource(R.drawable.ic_music_loop_true);
                    mediaPlayer.setLooping(true);

                }else{
                    buttonLoop.setBackgroundResource(R.drawable.music_loop_button_background_false);
                    buttonLoop.setImageResource(R.drawable.ic_music_loop_false);
                    mediaPlayer.setLooping(false);
                }
            }
        });
        */
    }
}
