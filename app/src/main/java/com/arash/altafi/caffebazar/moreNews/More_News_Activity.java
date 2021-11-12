package com.arash.altafi.caffebazar.moreNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.detailsNews.DetailsNews_Activity;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.arash.altafi.caffebazar.utility.OnLoadMoreListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import static com.arash.altafi.caffebazar.utility.intentKey.ID_NEWS;

public class More_News_Activity extends AppCompatActivity implements All_News_Adapter.OnCallBackClickNews {

    private ApiService apiService;
    private RecyclerView rc_all_news;
    private ImageView img_back;
    private All_News_Adapter all_news_adapter;
    private CoordinatorLayout coordinatorLayout;
    private List<ResponseNews> responseNews = new ArrayList<>();
    private int page = 1;
    private int loadIndex;
    private ProgressBar pr_Loading_News;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news);

        apiService = new ApiService(this);
        FindView();
        Listener();
        set_Default_Date();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                get_Data();
            }
        },500);
    }

    private void FindView()
    {
        rc_all_news = findViewById(R.id.rc_all_news);
        coordinatorLayout = findViewById(R.id.coordinator_all_news);
        pr_Loading_News = findViewById(R.id.pr_Loading_News);
        img_back = findViewById(R.id.img_back);
    }

    private void Listener()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void set_Default_Date()
    {
        rc_all_news.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        all_news_adapter = new All_News_Adapter(responseNews,rc_all_news,getApplicationContext(),More_News_Activity.this::onClickNews);
        rc_all_news.setAdapter(all_news_adapter);
        all_news_adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                if (responseNews.size() / 10 == page)
                {
                    page++;
                    responseNews.add(null);
                    loadIndex = responseNews.size() - 1;
                    all_news_adapter.notifyDataSetChanged();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            get_Data();
                        }
                    },2000);
                }
            }
        });
    }

    private void get_Data()
    {
        if (page == 1)
        {
            pr_Loading_News.setVisibility(View.VISIBLE);
        }
        apiService.get_More_News(page, new Response.Listener<List<ResponseNews>>() {
            @Override
            public void onResponse(List<ResponseNews> response) {
                if (page != 1)
                {
                    responseNews.remove(loadIndex);
                    all_news_adapter.notifyItemRemoved(loadIndex);
                }
                responseNews.addAll(response);
                all_news_adapter.notifyDataSetChanged();
                all_news_adapter.setLoading(false);
                pr_Loading_News.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout,"خطا در اتصال", Snackbar.LENGTH_LONG).show();
                pr_Loading_News.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClickNews(int id) {
        Intent intent = new Intent(getApplicationContext(), DetailsNews_Activity.class);
        intent.putExtra(ID_NEWS,id);
        startActivity(intent);
    }

}