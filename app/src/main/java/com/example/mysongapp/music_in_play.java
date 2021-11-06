package com.example.mysongapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.SystemClock;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.gauravk.audiovisualizer.visualizer.CircleLineVisualizer;

import java.io.File;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
public class music_in_play extends AppCompatActivity {
   static volatile the_way_of_play the_way=the_way_of_play.order;
static Button pause,skip_to_next_song,skip_to_pre_song,skip10sec,pre10sec,the_way_of_play_button;
static volatile MediaPlayer mediaPlayer;
static String[] sname;
static Stack<Integer> position_in_shuffle_for_move_to_next;
static Stack<Integer> position_in_shuffle_for_move_to_previous;
static  int position;
static ArrayList<File> musicList;
static TextView music_Name;
static ImageView imageView;
static SeekBar seekBar;
static TextView playingNow,max;
static CircleLineVisualizer visualizer;
static Thread update_seek_bar;
ConstraintLayout musicInplayParent;




    @Override
    protected void onDestroy() {

        if(visualizer != null)
        {
            visualizer.release();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("now playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_music_in_play);
        musicInplayParent = findViewById(R.id.musicInplayParent);
        pause=findViewById(R.id.pause);
        skip_to_next_song=findViewById(R.id.nextSong);
        skip_to_pre_song=findViewById(R.id.preSong);
        skip10sec=findViewById(R.id.next10);
        music_Name=findViewById(R.id.musicName);
        pre10sec=findViewById(R.id.pre10);
        the_way_of_play_button=findViewById(R.id.the_way_of_play);
        imageView = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        visualizer = findViewById(R.id.circle_circle);
        music_Name.setSelected(true);
        playingNow = findViewById(R.id.playingNow);
        max = findViewById(R.id.max);



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
        imageView.setImageBitmap(musicAdapter.createAlbumArt(musicList.get(position).toString()));

               //TODO: notification for show action while play music



// this thread will update the seekbar in every second
update_seek_bar=new Thread(){
    public void p(){


    }

    @Override
    public void run() {


        try {
for(;;) {
    int maxDuration = mediaPlayer.getDuration();
    int current_duration = 0;
    while (current_duration < maxDuration) {
        try {
            SystemClock.sleep(500);
            current_duration = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(current_duration);

        } catch (IllegalStateException e) {
            e.printStackTrace();

            SystemClock.sleep(250);

        }
    }
}
}catch(IllegalStateException e){ e.printStackTrace();

            SystemClock.sleep(250);}
    }
};

seekBar.setMax(mediaPlayer.getDuration());
update_seek_bar.start();
seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.av_dark_blue), PorterDuff.Mode.MULTIPLY);
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }
//stop the music when the user change the position of SeekBar so we can handle it with out glitsh
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        mediaPlayer.pause();
    }
//to follow the user when he use seek by seek bar
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar.getProgress()==mediaPlayer.getDuration())
        {
           move_to_next_song(true);
        }
        else{
        mediaPlayer.seekTo(seekBar.getProgress());
        mediaPlayer.start();}
    }
});

//our old thread
//        Thread_music thread_music=new Thread_music(musicList,getApplicationContext());
//        Thread t1=new Thread(thread_music);
//        t1.start();
        the_way_of_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(the_way==the_way_of_play.order){
                    the_way_of_play_button.setBackgroundResource(R.drawable.shuffle_image);
                    Toast.makeText(getApplicationContext(), "Shuffle", Toast.LENGTH_SHORT).show();
                    the_way=the_way_of_play.shuffle;
                    position_in_shuffle_for_move_to_next=new Stack<>();
                    position_in_shuffle_for_move_to_previous=new Stack<>();
                }
                else if(the_way==the_way_of_play.shuffle){
                    the_way_of_play_button.setBackgroundResource(R.drawable.rebeat_same_song);
                    Toast.makeText(getApplicationContext(), "Repeat same song", Toast.LENGTH_SHORT).show();
                    the_way=the_way_of_play.repeat_same_song;
                }
                else if(the_way==the_way_of_play.repeat_same_song){
                    the_way_of_play_button.setBackgroundResource(R.drawable.by_order);
                    Toast.makeText(getApplicationContext(), "By order", Toast.LENGTH_SHORT).show();
                    the_way=the_way_of_play.order;
                }
            }
        });
        Update update = new Update();
        update.execute(position);
//to stop and start music
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
        //to skip to next song
        skip_to_next_song.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           move_to_next_song(true);
        }
    });

    skip_to_pre_song.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            move_to_next_song(false);
        }
    });

    skip10sec.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try
            {
            if(mediaPlayer.getDuration()<=mediaPlayer.getCurrentPosition())
            {
               move_to_next_song(true);
            }
            else
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
            }
            catch (java.lang.IllegalStateException illegalStateException)
            {
                SystemClock.sleep(10);
            }
        }
    });

        pre10sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);

            }
        });
        if((mediaPlayer.getDuration()/1000)%60 < 10)
            max.setText(mediaPlayer.getDuration()/60000+":0"+(mediaPlayer.getDuration()/1000)%60+"");
        else
            max.setText(mediaPlayer.getDuration()/60000+":"+(mediaPlayer.getDuration()/1000)%60+"");
    }
//public static void update(){
//    music_Name.setText(sname[position]);
//    startAnimation(imageView,0f,360f);
//}
    public static void startAnimation(View view,float a, float b)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",a,b);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }


    public class Update extends AsyncTask<Integer,Integer,Void> {
        @Override
        protected Void doInBackground(Integer... integers) {

            for (;;) {
                try {
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            move_to_next_song(true);

                            music_Name.setText(sname[position]);
                            pause.setBackgroundResource(R.drawable.pause);
                            startAnimation(imageView, 0f, 360f);
                            if((mediaPlayer.getDuration()/1000)%60 <= 10)
                                max.setText(mediaPlayer.getDuration()/60000+":0"+(mediaPlayer.getDuration()/1000)%60+"");
                            else
                                max.setText(mediaPlayer.getDuration()/60000+":"+(mediaPlayer.getDuration()/1000)%60+"");
                        }
                    });

                } catch (java.lang.IllegalStateException illegalStateException) {
                    illegalStateException.printStackTrace();
                    SystemClock.sleep(10);
                }
                publishProgress(position);
                SystemClock.sleep(50);
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {


                if((seekBar.getProgress()/1000)%60 < 10)
                    playingNow.setText(seekBar.getProgress()/60000+":0"+(seekBar.getProgress()/1000)%60+"");
                else
                    playingNow.setText(seekBar.getProgress()/60000+":"+(seekBar.getProgress()/1000)%60+"");


        }
    }
    /**
     * this method do the what need to do when you move to next or previous song
     * true== move to next song
     * false == move to previous song
     *
    */
    public void move_to_next_song(boolean next_song){



            music_in_play.mediaPlayer.stop();
            music_in_play.mediaPlayer.release();
            control_the_increase(next_song);
            Uri uri = Uri.parse(musicList.get(music_in_play.position).toString());
            music_in_play.mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            music_in_play.mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
            if(visualizer != null)
            {
                visualizer.release();
                int audioseasonId = mediaPlayer.getAudioSessionId();
                if(audioseasonId != -1)
                    visualizer.setAudioSessionId(audioseasonId);
            }
            imageView.setImageBitmap(musicAdapter.createAlbumArt(musicList.get(position).toString()));
        music_Name.setText(sname[position]);
        pause.setBackgroundResource(R.drawable.pause);
        if(next_song)
            startAnimation(imageView,0f,360f);
        else
            startAnimation(imageView,360f,0f);

        if((mediaPlayer.getDuration()/1000)%60 < 10)
            max.setText(mediaPlayer.getDuration()/60000+":0"+(mediaPlayer.getDuration()/1000)%60+"");
        else
            max.setText(mediaPlayer.getDuration()/60000+":"+(mediaPlayer.getDuration()/1000)%60+"");
    }

    public void control_the_increase(boolean next_song){
        if(the_way==the_way_of_play.order){
            if(next_song){
                if (music_in_play.position == musicList.size() - 1)
                    music_in_play.position = 0;
                else
                    music_in_play.position++;
            }
            else{
                if(position == 0)
                    position=musicList.size()-1;
                else
                    position--;
            }
        }
        else if(the_way==the_way_of_play.shuffle){
            if(next_song){
                if(position_in_shuffle_for_move_to_previous.empty()){
                    position_in_shuffle_for_move_to_next.add(position);
                Random random=new Random();
                position=random.nextInt(musicList.size());
                }
                else{
                    position_in_shuffle_for_move_to_next.add(position);
                    position=position_in_shuffle_for_move_to_previous.pop();

                }
            }
            else{
                if(position_in_shuffle_for_move_to_next.isEmpty()){
                    position_in_shuffle_for_move_to_previous.add(position);
                    Random random=new Random();
                    position=random.nextInt(musicList.size());

                }
                else {
                    position_in_shuffle_for_move_to_previous.add(position);
                    position=position_in_shuffle_for_move_to_next.pop();
                }
            }
        }
        else if(the_way==the_way_of_play.repeat_same_song){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}