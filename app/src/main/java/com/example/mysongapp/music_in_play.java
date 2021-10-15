package com.example.mysongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.CircleLineVisualizer;

import java.io.File;
import java.util.ArrayList;

public class music_in_play extends AppCompatActivity {
Button pause,skip_to_next_song,skip_to_pre_song,skip10sec,pre10sec;
static  MediaPlayer mediaPlayer;
static String[] sname;
static  int position;
ArrayList<File> musicList;
static TextView music_Name;
static ImageView imageView;
SeekBar seekBar;
CircleLineVisualizer visualizer;
Thread update_seek_bar;
    @Override
    protected void onDestroy() {
        if(visualizer != null)
        {
            visualizer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_in_play);
        pause=findViewById(R.id.pause);
        skip_to_next_song=findViewById(R.id.nextSong);
        skip_to_pre_song=findViewById(R.id.preSong);
        skip10sec=findViewById(R.id.next10);
        music_Name=findViewById(R.id.musicName);
        pre10sec=findViewById(R.id.pre10);
        imageView = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        visualizer = findViewById(R.id.circle_circle);
        music_Name.setSelected(true);
if(mediaPlayer!=null)
{
    mediaPlayer.stop();
    mediaPlayer.release();
}
        Intent intent=getIntent();
    Bundle bundle=intent.getExtras();
    musicList=(ArrayList) bundle.getParcelableArrayList("songs");
    sname=bundle.getStringArray("name_of_song");
    music_Name.setSelected(true);
    position=bundle.getInt("position");

        Uri uri=Uri.parse(musicList.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        music_Name.setText(sname[position]);
               mediaPlayer.start();

               int audioseasonId = mediaPlayer.getAudioSessionId();
               if(audioseasonId != -1)
                visualizer.setAudioSessionId(audioseasonId);

update_seek_bar=new Thread(){
    @Override
    public void run() {
    int maxDuration=mediaPlayer.getDuration();
    int current_duration= 0;
    while (current_duration<mediaPlayer.getDuration()){
        try{
            Thread.sleep(500);
            current_duration=mediaPlayer.getCurrentPosition();
            seekBar.setProgress(current_duration);

        }catch (InterruptedException | IllegalStateException e){
            e.printStackTrace();
            try {
                Thread.sleep(250);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    }
};
seekBar.setMax(mediaPlayer.getDuration());
update_seek_bar.start();
seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.av_dark_blue), PorterDuff.Mode.MULTIPLY);
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
     mediaPlayer.seekTo(seekBar.getProgress());
    }
});
//        Thread_music thread_music=new Thread_music(musicList,getApplicationContext());
//        Thread t1=new Thread(thread_music);
//        t1.start();

        Update update = new Update();
        update.execute(position);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    pause.setBackgroundResource(R.drawable.play_arrow);
                    mediaPlayer.pause();
                }
                else{
                    pause.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });




        skip_to_next_song.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.stop();
            mediaPlayer.release();

            if(position == musicList.size()-1)
                position=0;
            else
                position++;
            music_Name.setText(sname[position]);
            Uri uri =Uri.parse(musicList.get(position).toString());
            pause.setBackgroundResource(R.drawable.pause);
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            startAnimation(imageView,0f,360f);

            if(visualizer != null)
            {
                visualizer.release();
                int audioseasonId = mediaPlayer.getAudioSessionId();
                if(audioseasonId != -1)
                    visualizer.setAudioSessionId(audioseasonId);
            }
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }
    });

    skip_to_pre_song.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.stop();
            mediaPlayer.release();

            if(position == 0)
                position=musicList.size()-1;
            else
                position--;
            music_Name.setText(sname[position]);
            Uri uri = Uri.parse(musicList.get(position).toString());
            pause.setBackgroundResource(R.drawable.pause);
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            startAnimation(imageView,360f,0f);
            if(visualizer != null)
            {
                visualizer.release();
                int audioseasonId = mediaPlayer.getAudioSessionId();
                if(audioseasonId != -1)
                    visualizer.setAudioSessionId(audioseasonId);
            }
            seekBar.setMax(mediaPlayer.getDuration());
        }
    });

    skip10sec.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if(mediaPlayer.getDuration()<=mediaPlayer.getCurrentPosition())
//            {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                if(position == musicList.size()-1)
//                    position=0;
//                else
//                    position++;
//                Uri uri = Uri.parse(musicList.get(position).toString());
//                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
//                mediaPlayer.start();
//            }
//            else
            try
            {

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                if(visualizer != null)
                {
                    visualizer.release();
                    int audioseasonId = mediaPlayer.getAudioSessionId();
                    if(audioseasonId != -1)
                        visualizer.setAudioSessionId(audioseasonId);
                }
            }
            catch (java.lang.IllegalStateException illegalStateException)
            {
                SystemClock.sleep(1000);
            }
        }
    });

        pre10sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);

            }
        });

    }
public static void update(){
    music_Name.setText(sname[position]);
    startAnimation(imageView,0f,360f);
}
    public static void startAnimation(View view,float a, float b)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",a,b);
        animator.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }
    public class Update extends AsyncTask<Integer,Integer,Void> {



        @Override
        protected Void doInBackground(Integer... integers) {
            for (; ; ) {
                try {

                    if (music_in_play.mediaPlayer.getCurrentPosition() >= music_in_play.mediaPlayer.getDuration()) {
                        music_in_play.mediaPlayer.stop();
                        music_in_play.mediaPlayer.release();
                        if (music_in_play.position == musicList.size() - 1)
                            music_in_play.position = 0;
                        else
                            music_in_play.position++;
                        Uri uri = Uri.parse(musicList.get(music_in_play.position).toString());
                        music_in_play.mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                        music_in_play.mediaPlayer.start();
                        publishProgress(position);

                        seekBar.setProgress(0);
                        seekBar.setMax(mediaPlayer.getDuration());
                        if(visualizer != null)
                        {
                            visualizer.release();
                            int audioseasonId = mediaPlayer.getAudioSessionId();
                            if(audioseasonId != -1)
                                visualizer.setAudioSessionId(audioseasonId);
                        }
                    }

                    SystemClock.sleep(1000);

                } catch (java.lang.IllegalStateException illegalStateException) {
                    SystemClock.sleep(1000);
                }
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            music_Name.setText(sname[position]);
            pause.setBackgroundResource(R.drawable.pause);
            startAnimation(imageView,0f,360f);

        }
    }
}