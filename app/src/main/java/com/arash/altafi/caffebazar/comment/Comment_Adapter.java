package com.arash.altafi.caffebazar.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.category.ResponseCategory;
import com.arash.altafi.caffebazar.utility.PriceConverter;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.ViewHolder> {

    List<ResponseComment> responseComments;
    Context context;

    public Comment_Adapter(List<ResponseComment> responseComments, Context context) {
        this.responseComments = responseComments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseComment comment = responseComments.get(position);
        holder.txt_author.setText(comment.getNameFamily());
        holder.txt_content.setText(comment.getContent());
        holder.txt_date.setText(comment.getDate());
        Glide.with(context).load(comment.getImage()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return responseComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView txt_author,txt_date,txt_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_content = itemView.findViewById(R.id.txt_content_commnet);
            txt_date = itemView.findViewById(R.id.txt_date_comment);
            txt_author = itemView.findViewById(R.id.txt_author_comment);
            circleImageView = itemView.findViewById(R.id.img_author_comment);
        }
    }

}
