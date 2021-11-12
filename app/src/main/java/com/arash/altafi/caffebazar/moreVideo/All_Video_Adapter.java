package com.arash.altafi.caffebazar.moreVideo;

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
import com.arash.altafi.caffebazar.home.modelhome.ResponseVideo;
import com.arash.altafi.caffebazar.utility.OnLoadMoreListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class All_Video_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ResponseVideo> responseVideos = new ArrayList<>();
    private OncallBackVideo oncallBackVideo;
    private Context context;

    private OnLoadMoreListener onLoadMoreListener;
    private int sizeIndex;
    private int lastVisible;
    private int countLoadMore = 2;
    private boolean isLoading = false;
    private final int VIEW_TYPE_RESPONSE = 1;
    private final int VIEW_TYPE_LOADMORE = 2;

    public All_Video_Adapter(List<ResponseVideo> responseVideos, RecyclerView recyclerView ,OncallBackVideo oncallBackVideo, Context context) {
        this.responseVideos = responseVideos;
        this.oncallBackVideo = oncallBackVideo;
        this.context = context;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sizeIndex = layoutManager.getItemCount();
                lastVisible = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && sizeIndex <= (lastVisible + countLoadMore))
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

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (responseVideos.get(position) != null)
            return VIEW_TYPE_RESPONSE;
        else
            return VIEW_TYPE_LOADMORE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_RESPONSE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_video,parent,false);
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
            ResponseVideo video = responseVideos.get(position);
            Glide.with(context).load(video.getVideoimage()).into(((ViewHolder) holder).imgVideo);
            ((ViewHolder) holder).txtTime.setText(video.getVideotime());
            ((ViewHolder) holder).txtTitle.setText(video.getTitle());
            ((ViewHolder) holder).imgVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oncallBackVideo.onClickVideo(video.getLink(),video.getTitle(),video.getVideoimage());
                }
            });
        }
        else if (holder instanceof LoadMoreViewHolder)
        {
            ResponseVideo video = responseVideos.get(position);
            ((LoadMoreViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return responseVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txtTitle;
        private ImageView imgVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVideo = itemView.findViewById(R.id.img_video_main);
            txtTitle = itemView.findViewById(R.id.txt_title_video_main);
            txtTime = itemView.findViewById(R.id.txt_time_video_main);
        }
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pr_Loading_More);
        }
    }

    public interface OncallBackVideo {
        void onClickVideo(String link, String title, String image);
    }

}
