package com.example.mysongapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MusicFragment extends Fragment {

    TextView musicName;
    Button pause;
    Button nextSong;
    Button preSong;
    RelativeLayout fragmentParent;
    Context context;

    public MusicFragment(Context context) {
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment, container, false);

        musicName = view.findViewById(R.id.fragmentMusicSong);
        pause = view.findViewById(R.id.fragmentPause);
        nextSong = view.findViewById(R.id.fragmentNextSong);
        preSong = view.findViewById(R.id.fragmentPreSong);
        fragmentParent = view.findViewById(R.id.fragmentParent);

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_in_play.skip_to_next_song.performClick();
                pause.setBackground(music_in_play.pause.getBackground());
                musicName.setText(music_in_play.music_Name.getText());
            }
        });

        preSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_in_play.skip_to_pre_song.performClick();
                pause.setBackground(music_in_play.pause.getBackground());
                musicName.setText(music_in_play.music_Name.getText());
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music_in_play.pause.performClick();
                pause.setBackground(music_in_play.pause.getBackground());
            }
        });

        musicName.setSelected(true);



        fragmentParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(musicAdapter.intent);
            }
        });


        return view;
    }




    @Override
    public void onResume() {
        super.onResume();
        if(musicAdapter.pos == -1)
            fragmentParent.setEnabled(false);
        else
            fragmentParent.setEnabled(true);
        if (music_in_play.pause == null)
        {
            pause.setEnabled(false);
            pause.setBackgroundResource(R.drawable.play_arrow);
        }
        else
        {
            pause.setEnabled(true);
            pause.setBackgroundResource(R.drawable.pause);
        }

        if (music_in_play.skip_to_next_song == null)
            nextSong.setEnabled(false);
        else
            nextSong.setEnabled(true);

        if (music_in_play.skip_to_pre_song == null)
            preSong.setEnabled(false);
        else
            preSong.setEnabled(true);

        if(music_in_play.music_Name != null)
            musicName.setText(music_in_play.music_Name.getText());

        if(music_in_play.pause != null)
            pause.setBackground(music_in_play.pause.getBackground());
    }
}
