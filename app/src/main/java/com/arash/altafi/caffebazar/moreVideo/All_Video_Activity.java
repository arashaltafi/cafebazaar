package com.arash.altafi.caffebazar.moreVideo;

import androidx.appcompat.app.AppCompatActivity;
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
import com.arash.altafi.caffebazar.home.modelhome.ResponseVideo;
import com.arash.altafi.caffebazar.playVideo.Play_Video_Activity;
import com.arash.altafi.caffebazar.utility.OnLoadMoreListener;
import com.arash.altafi.caffebazar.utility.intentKey;

import java.util.ArrayList;
import java.util.List;

public class All_Video_Activity extends AppCompatActivity implements All_Video_Adapter.OncallBackVideo {

    private All_Video_Adapter all_video_adapter;
    private RecyclerView rc_Video;
    private ImageView img_Back;
    private ProgressBar pr_Loading_Videos;
    private ApiService apiService;

    //paging
    private int page = 1;
    private List<ResponseVideo> responseVideos = new ArrayList<>();
    private int loadIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video);

        apiService = new ApiService(this);

        FindView();
        Listener();
        set_Default_Data();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                get_All_Video();
            }
        },500);
    }

    private void FindView()
    {
        img_Back = findViewById(R.id.img_back);
        rc_Video = findViewById(R.id.rc_all_videos);
        pr_Loading_Videos = findViewById(R.id.pr_Loading_Videos);
    }

    private void Listener()
    {
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void set_Default_Data()
    {
        rc_Video.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        all_video_adapter = new All_Video_Adapter(responseVideos,rc_Video,All_Video_Activity.this::onClickVideo,getApplicationContext());
        rc_Video.setAdapter(all_video_adapter);
        all_video_adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                if (responseVideos.size() / 5 == page)
                {
                    page++;
                    responseVideos.add(null);
                    loadIndex = responseVideos.size() - 1;
                    all_video_adapter.notifyDataSetChanged();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            get_All_Video();
                        }
                    },2000);
                }
            }
        });
    }

    private void get_All_Video()
    {
        if (page == 1)
        {
            pr_Loading_Videos.setVisibility(View.VISIBLE);
        }
        apiService.get_All_Video(page,new Response.Listener<List<ResponseVideo>>() {
            @Override
            public void onResponse(List<ResponseVideo> response) {
                if (page != 1)
                {
                    responseVideos.remove(loadIndex);
                    all_video_adapter.notifyItemRemoved(loadIndex);
                }
                responseVideos.addAll(response);
                all_video_adapter.notifyDataSetChanged();
                all_video_adapter.setLoading(false);
                pr_Loading_Videos.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(All_Video_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickVideo(String link, String title, String image) {
        Intent intent = new Intent(getApplicationContext(), Play_Video_Activity.class);
        intent.putExtra(intentKey.LINK_VIDEO,link);
        intent.putExtra(intentKey.TITLE_VIDEO,title);
        intent.putExtra(intentKey.IMAGE_VIDEO,image);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}