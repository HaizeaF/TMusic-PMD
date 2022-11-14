package com.example.tmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {

    private ImageButton buttonStartPause;
    private ImageButton buttonNextSong;
    private ImageButton buttonPreviousSong;
    private ImageButton buttonLoop;
    private ImageButton buttonBack;
    private SeekBar seekBarMusic;
    private TextView textElapsedTime;
    private TextView textTimeLeft;
    private MediaPlayer mediaPlayer;
    private VideoView videoSong;
    private String author;
    private String song;
    private String email;
    private Integer authorId;
    private Integer songResID;
    private TextView textSongName;
    private TextView textSongAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        buttonBack = findViewById(R.id.buttonBack);
        buttonNextSong = findViewById(R.id.buttonNextSong);
        buttonPreviousSong = findViewById(R.id.buttonPreviousSong);

        Bundle extras = getIntent().getExtras();
        author = extras.getString("author");
        authorId = extras.getInt("authorId");
        song = extras.getString("song");
        email = extras.getString("email");

        textSongName = findViewById(R.id.textSongName);
        textSongAuthor = findViewById(R.id.textSongAuthor);

        textSongName.setText(song);
        textSongAuthor.setText(author);

        //Video
        videoSong = findViewById(R.id.videoSong);

        initPlayer();

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying()){
                    buttonStartPause.setBackgroundResource(R.drawable.ic_music_pause);
                    videoSong.start();
                    mediaPlayer.start();
                }else{
                    buttonStartPause.setBackgroundResource(R.drawable.ic_music_play);
                    videoSong.pause();
                    mediaPlayer.pause();
                }
            }
        });


        buttonLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isLooping()){
                    Toast toast =Toast.makeText(getApplicationContext(),R.string.notImplemented, Toast.LENGTH_SHORT);
                    toast.show();

                }else{
                    Toast toast =Toast.makeText(getApplicationContext(),R.string.notImplemented, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });



        buttonPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =Toast.makeText(getApplicationContext(),R.string.notImplemented, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        buttonNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =Toast.makeText(getApplicationContext(),R.string.notImplemented, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(mediaPlayer.getDuration());
                textTimeLeft.setText(totalTime);
                seekBarMusic.setMax(videoSong.getDuration());
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                buttonStartPause.setBackgroundResource(R.drawable.ic_music_pause);
                videoSong.stopPlayback();
            }
        });
    }

    private void initPlayer() {

        Context context = getBaseContext();
        songResID = context.getResources().getIdentifier("raw/"+song.toLowerCase(Locale.ROOT), null, context.getPackageName());

        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + songResID);

        videoSong.setVideoURI(path);
        videoSong.start();

        //Music Controller
        buttonStartPause = findViewById(R.id.buttonStartPause);
        buttonLoop = findViewById(R.id.buttonLoop);
        textElapsedTime = findViewById(R.id.textElapsedTime);
        textTimeLeft = findViewById(R.id.textTimeLeft);

        mediaPlayer = MediaPlayer.create(this, songResID);
        videoSong.seekTo(0);
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        videoSong.start();
        mediaPlayer.setVolume(0f,0f);

        seekBarMusic = findViewById(R.id.seekBarMusic);

        videoSong.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(mediaPlayer.getDuration());
                textTimeLeft.setText(totalTime);
                seekBarMusic.setMax(videoSong.getDuration());
            }
        });

        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBarMusic.setProgress(progress);
                    videoSong.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (videoSong != null) {
                    try {
                        if (videoSong.isPlaying()) {
                            Message msg = new Message();
                            msg.what = videoSong.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                            buttonBack.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), SongsActivity.class);
                                    intent.putExtra("author", authorId);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();
                                    Thread.interrupted();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int current_position = msg.what;
            seekBarMusic.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            textElapsedTime.setText(cTime);
        }
    };

    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }
}
