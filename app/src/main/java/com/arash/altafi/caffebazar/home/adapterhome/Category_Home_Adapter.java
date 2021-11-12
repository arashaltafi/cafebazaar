package com.arash.altafi.caffebazar.home.adapterhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseHomeCategory;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_Home_Adapter extends RecyclerView.Adapter<Category_Home_Adapter.ViewHolder> {

    List<ResponseHomeCategory> responseHomeCategories = new ArrayList<>();
    private OnCallBackClickCatHom onCallBackClickCatHom;

    public Category_Home_Adapter(List<ResponseHomeCategory> responseHomeCategories, OnCallBackClickCatHom onCallBackClickNews) {
        this.responseHomeCategories = responseHomeCategories;
        this.onCallBackClickCatHom = onCallBackClickNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseHomeCategory homeCategory = responseHomeCategories.get(position);
        holder.txtCategory.setText(homeCategory.getNameCat());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackClickCatHom.onClickCatHome(Integer.parseInt(homeCategory.getIdCat()),homeCategory.getNameCat());
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseHomeCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCategory;
        private RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txt_home_category);
            relativeLayout = itemView.findViewById(R.id.rltv_home_category);
        }
    }

    public interface OnCallBackClickCatHom
    {
        void onClickCatHome(int id,String name);
    }

}
