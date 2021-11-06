package com.example.mysongapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class StartActivity extends AppCompatActivity {
    static Button allSongs;
    static Button playlist;
    static Button favorite;
    static Button albums ;
    static RelativeLayout startActivityParent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent intent = new Intent(StartActivity.this,SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingsActivity.b) {
            startActivityParent.setBackgroundResource(R.mipmap.wall_wall_foreground);
            albums.setBackgroundResource(R.drawable.start_bg);
            favorite.setBackgroundResource(R.drawable.start_bg);
            playlist.setBackgroundResource(R.drawable.start_bg);
            allSongs.setBackgroundResource(R.drawable.start_bg);
        }
        else
        {
            startActivityParent.setBackgroundResource(R.color.white);
           albums.setBackgroundResource(R.drawable.start_bg2);
           favorite.setBackgroundResource(R.drawable.start_bg2);
           playlist.setBackgroundResource(R.drawable.start_bg2);
           allSongs.setBackgroundResource(R.drawable.start_bg2);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startActivityParent = findViewById(R.id.startActivityParent);
        playlist = findViewById(R.id.playlists);
        favorite = findViewById(R.id.favorite);
        albums = findViewById(R.id.albums);


        allSongs = findViewById(R.id.allsongs);
        allSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }


}