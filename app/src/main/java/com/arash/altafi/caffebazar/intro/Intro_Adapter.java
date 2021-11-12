package com.arash.altafi.caffebazar.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class Intro_Adapter extends RecyclerView.Adapter<Intro_Adapter.IntroViewHolder> {

    private List<Intro_Model> intro_models = new ArrayList<>();
    private Context context;

    public Intro_Adapter(List<Intro_Model> intro_models, Context context) {
        this.intro_models = intro_models;
        this.context = context;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intro, parent, false);
        return new IntroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        Intro_Model introModel = intro_models.get(position);
        Glide.with(context).load(introModel.getImage()).into(holder.img_intro);
        holder.txt_title.setText(introModel.getTitle());
        holder.txt_content.setText(introModel.getContent());
    }

    @Override
    public int getItemCount() {
        return intro_models.size();
    }

    public class IntroViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_intro;
        private TextView txt_title, txt_content;

        public IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            img_intro = itemView.findViewById(R.id.img_intro);
            txt_content = itemView.findViewById(R.id.txt_content_intro);
            txt_title = itemView.findViewById(R.id.txt_title_intro);
        }

    }

}
