package com.arash.altafi.caffebazar.home.adapterhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseEducation;

import java.util.ArrayList;
import java.util.List;

public class Education_Adapter extends RecyclerView.Adapter<Education_Adapter.ViewHolder> {

    private List<ResponseEducation> responseEducations = new ArrayList<>();

    public Education_Adapter(List<ResponseEducation> responseEducations) {
        this.responseEducations = responseEducations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_education,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseEducation education = responseEducations.get(position);
        holder.txtTitle.setText(education.getEducationTitle());
        holder.txtCategory.setText(education.getCatname());
        holder.pr_Education.setProgress(education.getCapacity());
        holder.txtCapacity.setText(String.valueOf(education.getCapacity()));
    }

    @Override
    public int getItemCount() {
        return responseEducations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCategory,txtTitle,txtCapacity;
        private ProgressBar pr_Education;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txt_category_education);
            txtTitle = itemView.findViewById(R.id.txt_title_education);
            txtCapacity = itemView.findViewById(R.id.txt_capacity);
            pr_Education = itemView.findViewById(R.id.pr_score_education);
        }

        public void bindEducation(ResponseEducation education)
        {
            txtCategory.setText(education.getCatname());
            txtTitle.setText(education.getEducationTitle());
            pr_Education.setProgress(education.getCapacity());
        }

    }

}
