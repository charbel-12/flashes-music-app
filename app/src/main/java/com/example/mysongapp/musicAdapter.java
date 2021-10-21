package com.example.mysongapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class musicAdapter extends RecyclerView.Adapter<musicAdapter.ViewHolder> {
musicUtils musicUtils;
Context context;
static Intent intent;
static int pos = -1;
    public musicAdapter(com.example.mysongapp.musicUtils musicUtils, Context context) {
        this.musicUtils = musicUtils;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtName.setText(musicUtils.nameofsongs[position]);
    }

    @Override
    public int getItemCount() {
        return musicUtils.nameofsongs.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
             txtName.setSelected(true);
            ConstraintLayout layout = itemView.findViewById(R.id.parent);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                 intent = new Intent(context,music_in_play.class).putExtra("songs",musicUtils.songsFiles)
                            .putExtra("position",getAdapterPosition())
                            .putExtra("name_of_song",musicUtils.nameofsongs);

                    context.startActivity(intent);
                }
            });
        }

    }

}
