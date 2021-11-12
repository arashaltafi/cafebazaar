package com.arash.altafi.caffebazar.favourite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.detailsNews.DetailsNews_Activity;
import com.arash.altafi.caffebazar.home.adapterhome.News_Adapter;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Favourite_Activity extends AppCompatActivity implements News_Adapter.OnCallBackClickNews {

    private CoordinatorLayout coordinator_favourite;
    private ImageView imgBack;
    private News_Adapter news_adapter;
    private RecyclerView rc_favourite;
    private ProgressBar pr_favourite;
    private ApiService apiService;
    private RelativeLayout rltv_lottie_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        apiService = new ApiService(this);

        FindView();
        Listener();
//        getData();
    }

    private void FindView()
    {
        coordinator_favourite = findViewById(R.id.coordinator_favourite);
        rc_favourite = findViewById(R.id.rc_favourite);
        pr_favourite = findViewById(R.id.pr_favourite);
        imgBack = findViewById(R.id.img_back);
        rltv_lottie_empty = findViewById(R.id.rltv_lottie_empty);
    }

    private void Listener()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData()
    {
        apiService.get_favourite(TokenContainer.getToken(), new Response.Listener<List<ResponseNews>>() {
            @Override
            public void onResponse(List<ResponseNews> response) {
                if (response.isEmpty())
                {
                    rltv_lottie_empty.setVisibility(View.VISIBLE);
                }
                else
                {
                    rltv_lottie_empty.setVisibility(View.GONE);
                }
                news_adapter = new News_Adapter(response,getApplicationContext(),Favourite_Activity.this::onClickNews);
                rc_favourite.setAdapter(news_adapter);
                rc_favourite.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                pr_favourite.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinator_favourite, "خطا در اتصال" ,Snackbar.LENGTH_SHORT).show();
                pr_favourite.setVisibility(View.GONE);
                finish();
            }
        });
    }

    @Override
    public void onClickNews(int id) {
        Intent intent = new Intent(getApplicationContext(), DetailsNews_Activity.class);
        intent.putExtra(intentKey.ID_NEWS,id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}