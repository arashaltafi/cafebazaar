package com.arash.altafi.caffebazar.morePodcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.detailsPodcast.Podcast_Activity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.arash.altafi.caffebazar.utility.intentKey.AUTHOR_IMAGE_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.AUTHOR_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.ID_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.IMAGE_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.LINK_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.TITLE_PODCAST;

public class More_Podcast_Activity extends AppCompatActivity implements More_Padcast_Adapter.OnCallBackPodcast {

    private ApiService apiService;
    private RecyclerView rc_all_podcasts;
    private ImageView img_back;
    private More_Padcast_Adapter more_padcast_adapter;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar pr_Loading_Podcasts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_podcast);
        apiService = new ApiService(this);

        FindView();
        Listener();
        get_Data();
    }

    private void FindView()
    {
        rc_all_podcasts = findViewById(R.id.rc_all_podcasts);
        coordinatorLayout = findViewById(R.id.coordinator_all_podcasts);
        pr_Loading_Podcasts = findViewById(R.id.pr_Loading_Podcasts);
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

    private void get_Data()
    {
        apiService.get_More_Podcasts(new Response.Listener<List<ResponseMorePodcast>>() {
            @Override
            public void onResponse(List<ResponseMorePodcast> response) {
                pr_Loading_Podcasts.setVisibility(View.GONE);
                more_padcast_adapter = new More_Padcast_Adapter(response,getApplicationContext(),More_Podcast_Activity.this::onClickPodcast);
                rc_all_podcasts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rc_all_podcasts.setAdapter(more_padcast_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout,"خطا در اتصال",Snackbar.LENGTH_LONG).show();
                pr_Loading_Podcasts.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClickPodcast(int id, String title, String link, String autherName, String autherImage, String image) {
        Intent intent = new Intent(getApplicationContext(), Podcast_Activity.class);
        intent.putExtra(ID_PODCAST,id);
        intent.putExtra(TITLE_PODCAST,title);
        intent.putExtra(LINK_PODCAST,link);
        intent.putExtra(AUTHOR_PODCAST,autherName);
        intent.putExtra(AUTHOR_IMAGE_PODCAST,autherImage);
        intent.putExtra(IMAGE_PODCAST,image);
        startActivity(intent);
    }
}