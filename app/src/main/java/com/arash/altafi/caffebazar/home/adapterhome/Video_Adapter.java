package com.arash.altafi.caffebazar.home.adapterhome;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseVideo;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {

    List<ResponseVideo> responseVideos = new ArrayList<>();
    private OncallBackVideo oncallBackVideo;
    Context context;

    public Video_Adapter(List<ResponseVideo> responseVideos, OncallBackVideo oncallBackVideo, Context context) {
        this.responseVideos = responseVideos;
        this.oncallBackVideo = oncallBackVideo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseVideo video = responseVideos.get(position);
        Glide.with(context).load(video.getVideoimage()).into(holder.imgVideo);
        holder.txtTime.setText(video.getVideotime());
        holder.txtTitle.setText(video.getTitle());
        holder.card_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oncallBackVideo.onClickVideo(video.getLink(),video.getTitle(),video.getVideoimage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txtTitle;
        private ImageView imgVideo;
        private MaterialCardView card_video;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVideo = itemView.findViewById(R.id.img_video_main);
            txtTitle = itemView.findViewById(R.id.txt_title_video_main);
            txtTime = itemView.findViewById(R.id.txt_time_video_main);
            card_video = itemView.findViewById(R.id.card_video);
        }
    }

    public interface OncallBackVideo {
        void onClickVideo(String link, String title, String image);
    }

}
