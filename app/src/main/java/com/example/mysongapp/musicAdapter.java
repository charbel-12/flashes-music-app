package com.example.mysongapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
   // holder.image2.setImageBitmap(createAlbumArt(musicUtils.songsFiles.get(position).toString()));
    }

    @Override
    public int getItemCount() {
        return musicUtils.nameofsongs.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView image2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image2=itemView.findViewById(R.id.img2);
            txtName = itemView.findViewById(R.id.txtName);
//             txtName.setSelected(true);
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
    public static Bitmap createAlbumArt(final String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            byte[] embedPic = retriever.getEmbeddedPicture();
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }
}
