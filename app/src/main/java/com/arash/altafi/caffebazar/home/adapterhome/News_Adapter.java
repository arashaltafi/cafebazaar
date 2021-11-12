package com.arash.altafi.caffebazar.home.adapterhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder> {

    List<ResponseNews> responseNews = new ArrayList<>();
    Context context;
    private OnCallBackClickNews onCallBackClickNews;

    public News_Adapter(List<ResponseNews> responseNews, Context context, OnCallBackClickNews onCallBackClickNews) {
        this.responseNews = responseNews;
        this.context = context;
        this.onCallBackClickNews = onCallBackClickNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseNews news = responseNews.get(position);
        holder.txtTitle.setText(news.getTitle());
        holder.txtContent.setText(news.getContent());
        holder.txtAuthor.setText(news.getName());
        holder.txtDate.setText(news.getDate());
        Glide.with(context).load(news.getImage()).into(holder.imgNews);
        Glide.with(context).load(news.getWriterImage()).into(holder.imgAuthor);
        holder.card_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackClickNews.onClickNews(Integer.parseInt(news.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtContent,txtTitle,txtAuthor,txtDate;
        private CircleImageView imgAuthor;
        private ImageView imgNews;
        private MaterialCardView card_news;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_date_news);
            txtAuthor = itemView.findViewById(R.id.txt_author_news);
            txtContent = itemView.findViewById(R.id.txt_content_news);
            txtTitle = itemView.findViewById(R.id.txt_title_news);
            imgAuthor = itemView.findViewById(R.id.img_author_news);
            imgNews = itemView.findViewById(R.id.img_news);
            card_news = itemView.findViewById(R.id.card_news);
        }
    }

    public interface OnCallBackClickNews
    {
        void onClickNews(int id);
    }

}
