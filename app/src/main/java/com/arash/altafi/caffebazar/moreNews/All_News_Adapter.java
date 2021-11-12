package com.arash.altafi.caffebazar.moreNews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.arash.altafi.caffebazar.utility.OnLoadMoreListener;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class All_News_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ResponseNews> responseNews = new ArrayList<>();
    Context context;
    private OnCallBackClickNews onCallBackClickNews;
    private OnLoadMoreListener onLoadMoreListener;

    private int sizeIndex;
    private int lastVisible;
    private int countLoadmore = 2;
    private boolean isLoading = false;
    private final int VIEW_TYPE_RESPONSE = 1;
    private final int VIEW_TYPE_LOADMORE = 2;

    public All_News_Adapter(List<ResponseNews> responseNews,RecyclerView recyclerView, Context context, OnCallBackClickNews onCallBackClickNews) {
        this.responseNews = responseNews;
        this.context = context;
        this.onCallBackClickNews = onCallBackClickNews;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sizeIndex = layoutManager.getItemCount();
                lastVisible = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && sizeIndex <= (lastVisible + countLoadmore))
                {
                    if (onLoadMoreListener != null)
                    {
                        onLoadMoreListener.loadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public int getItemViewType(int position) {
        if (responseNews.get(position) != null)
        {
            return VIEW_TYPE_RESPONSE;
        }
        else
        {
            return VIEW_TYPE_LOADMORE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_RESPONSE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
            return new ViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADMORE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more,parent,false);
            return new LoadMoreViewHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder)
        {
            ResponseNews news = responseNews.get(position);
            ((ViewHolder) holder).txtTitle.setText(news.getTitle());
            ((ViewHolder) holder).txtContent.setText(news.getContent());
            ((ViewHolder) holder).txtAuthor.setText(news.getName());
            ((ViewHolder) holder).txtDate.setText(news.getDate());
            Glide.with(context).load(news.getImage()).into(((ViewHolder) holder).imgNews);
            Glide.with(context).load(news.getWriterImage()).into(((ViewHolder) holder).imgAuthor);
            ((ViewHolder) holder).card_news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCallBackClickNews.onClickNews(Integer.parseInt(news.getId()));

                }
            });
        }
        else if (holder instanceof LoadMoreViewHolder)
        {
            ((LoadMoreViewHolder) holder).progressBar.setIndeterminate(true);
        }

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

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pr_Loading_More);
        }
    }

    public interface OnCallBackClickNews
    {
        void onClickNews(int id);

    }

}
