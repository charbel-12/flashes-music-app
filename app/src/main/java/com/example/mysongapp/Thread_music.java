package com.example.mysongapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.File;
import java.util.ArrayList;

public class Thread_music implements Runnable
{
    ArrayList<File> musicList;

    Context context;
    public Thread_music(ArrayList<File> musicList,Context context) {
        this.musicList = musicList;
        this.context=context;
    }

    @Override
    public void run() {
     for(;;){
       try {

         if(music_in_play.mediaPlayer.getCurrentPosition()>=music_in_play.mediaPlayer.getDuration()){
             music_in_play.mediaPlayer.stop();
             music_in_play.mediaPlayer.release();
             if(music_in_play.position == musicList.size()-1)
                    music_in_play.position=0;
             else
                   music_in_play.position++;
//            music_in_play.update();
             Uri uri = Uri.parse(musicList.get(music_in_play.position).toString());
             music_in_play.mediaPlayer = MediaPlayer.create(context,uri);
             music_in_play.mediaPlayer.start();

         }

             SystemClock.sleep(1000);

     }
       catch (java.lang.IllegalStateException illegalStateException){
           SystemClock.sleep(1000);
       }

     }
    }

}
