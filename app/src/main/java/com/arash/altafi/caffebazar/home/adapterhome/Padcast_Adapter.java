package com.arash.altafi.caffebazar.home.adapterhome;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponsePadcast;
import com.arash.altafi.caffebazar.utility.Utilities;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Padcast_Adapter extends RecyclerView.Adapter<Padcast_Adapter.ViewHolder> {

    List<ResponsePadcast> responsePadcasts = new ArrayList<>();
    Context context;
    OnCallBackPodcast onCallBackPodcast;

    public Padcast_Adapter(List<ResponsePadcast> responsePadcasts, Context context, OnCallBackPodcast onCallBackPodcast) {
        this.responsePadcasts = responsePadcasts;
        this.context = context;
        this.onCallBackPodcast = onCallBackPodcast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_padcast,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponsePadcast padcast = responsePadcasts.get(position);
        holder.txtTitle.setText(padcast.getTitle());
        holder.txtAuthor.setText(padcast.getWriterName());
//        holder.txtTime.setText(padcast.getPadTime());
        Glide.with(context).load(padcast.getPadimage()).into(holder.imgPadacast);
        Glide.with(context).load(padcast.getWriterImage()).into(holder.imgAuthor);
        holder.card_podcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackPodcast.onClickPodcast(padcast.getPadcastId(),padcast.getTitle(),
                        padcast.getLink(),padcast.getWriterName(),padcast.getWriterImage(),padcast.getPadimage());
            }
        });

        // دریافت تایم موزیک مستقیم از لینک
        Utilities utilities;
        utilities = new Utilities();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse(padcast.getLink()));
        int time = mediaPlayer.getDuration();
        holder.txtTime.setText(utilities.milliSecondsToTimer(time) + " " + "دقیقه");
    }

    @Override
    public int getItemCount() {
        return responsePadcasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtAuthor;
        private TextView txtTime;
        private CircleImageView imgAuthor;
        private ImageView imgPadacast;
        private CoordinatorLayout card_podcast;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAuthor = itemView.findViewById(R.id.txt_auther_padcast);
            txtTitle = itemView.findViewById(R.id.txt_title_padcast);
            txtTime = itemView.findViewById(R.id.txt_time_padcast);
            imgAuthor = itemView.findViewById(R.id.img_author_padcast);
            imgPadacast = itemView.findViewById(R.id.img_padcast);
            card_podcast = itemView.findViewById(R.id.card_podcast);
        }
    }

    public interface OnCallBackPodcast
    {
        void onClickPodcast(int id,String title, String link,String autherName,String autherImage,String image);
    }

}
