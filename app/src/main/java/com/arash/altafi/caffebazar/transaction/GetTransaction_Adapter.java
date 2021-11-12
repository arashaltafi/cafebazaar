package com.arash.altafi.caffebazar.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseEducation;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class GetTransaction_Adapter extends RecyclerView.Adapter<GetTransaction_Adapter.ViewHolder> {

    private List<ResponseGetTransaction> responseGetTransactions = new ArrayList<>();
    private onClickRefId onClickRefId;

    public GetTransaction_Adapter(List<ResponseGetTransaction> responseGetTransactions, GetTransaction_Adapter.onClickRefId onClickRefId) {
        this.responseGetTransactions = responseGetTransactions;
        this.onClickRefId = onClickRefId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trancation,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseGetTransaction transaction = responseGetTransactions.get(position);
        holder.txtTitle.setText(transaction.getTitle());
        holder.txtDate.setText(transaction.getDate());
        holder.txtRef_Id.setText(transaction.getRefId());
        holder.card_Transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRefId.onClickRef(transaction.getRefId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseGetTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle,txtDate,txtRef_Id;
        private MaterialCardView card_Transaction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRef_Id = itemView.findViewById(R.id.txt_refId_Product);
            txtTitle = itemView.findViewById(R.id.txt_title_product_buy);
            txtDate = itemView.findViewById(R.id.txt_date_product_buy);
            card_Transaction = itemView.findViewById(R.id.card_Transaction);
        }

    }

    public interface onClickRefId
    {
        void onClickRef (String ref);
    }

}
