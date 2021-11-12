package com.arash.altafi.caffebazar.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.utility.PriceConverter;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {

    List<ResponseCategory> responseCategories;
    private OnCallBackProduct onCallBackProduct;
    Context context;

    public Category_Adapter(List<ResponseCategory> responseCategories, OnCallBackProduct onCallBackProduct, Context context) {
        this.responseCategories = responseCategories;
        this.onCallBackProduct = onCallBackProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseCategory category = responseCategories.get(position);
        holder.txt_title.setText(category.getTitle());
        holder.txt_price.setText(PriceConverter.priceConvert(category.getPrice()));
        Glide.with(context).load(category.getImage()).into(holder.img_product);
        holder.card_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackProduct.onClickProduct(category.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView card_category;
        private TextView txt_title,txt_price;
        private ImageView img_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_category = itemView.findViewById(R.id.card_category);
            txt_price = itemView.findViewById(R.id.txt_price_product);
            txt_title = itemView.findViewById(R.id.txt_title_product);
            img_product = itemView.findViewById(R.id.img_product);
        }
    }

    public interface OnCallBackProduct
    {
        void onClickProduct(int id);
    }

}
