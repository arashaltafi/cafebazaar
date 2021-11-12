package com.arash.altafi.caffebazar.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.category.Category_Activity;
import com.arash.altafi.caffebazar.detailsNews.DetailsNews_Activity;
import com.arash.altafi.caffebazar.home.adapterhome.Category_Home_Adapter;
import com.arash.altafi.caffebazar.home.adapterhome.Education_Adapter;
import com.arash.altafi.caffebazar.home.adapterhome.News_Adapter;
import com.arash.altafi.caffebazar.home.adapterhome.Padcast_Adapter;
import com.arash.altafi.caffebazar.home.adapterhome.Video_Adapter;
import com.arash.altafi.caffebazar.home.modelhome.ResponseEducation;
import com.arash.altafi.caffebazar.home.modelhome.ResponseHomeCategory;
import com.arash.altafi.caffebazar.home.modelhome.ResponseNews;
import com.arash.altafi.caffebazar.home.modelhome.ResponsePadcast;
import com.arash.altafi.caffebazar.home.modelhome.ResponseVideo;
import com.arash.altafi.caffebazar.moreNews.More_News_Activity;
import com.arash.altafi.caffebazar.morePodcast.More_Podcast_Activity;
import com.arash.altafi.caffebazar.playVideo.Play_Video_Activity;
import com.arash.altafi.caffebazar.detailsPodcast.Podcast_Activity;
import com.arash.altafi.caffebazar.profile.Profile_Activity;
import com.arash.altafi.caffebazar.subcribe.SubScribe_Activity;
import com.arash.altafi.caffebazar.utility.CenterZoomLayoutManager;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.arash.altafi.caffebazar.moreVideo.All_Video_Activity;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;
import static com.arash.altafi.caffebazar.utility.intentKey.AUTHOR_IMAGE_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.AUTHOR_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.ID_NEWS;
import static com.arash.altafi.caffebazar.utility.intentKey.ID_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.IMAGE_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.IMAGE_VIDEO;
import static com.arash.altafi.caffebazar.utility.intentKey.LINK_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.LINK_VIDEO;
import static com.arash.altafi.caffebazar.utility.intentKey.TITLE_PODCAST;
import static com.arash.altafi.caffebazar.utility.intentKey.TITLE_VIDEO;

public class Home_Activity extends AppCompatActivity implements Video_Adapter.OncallBackVideo, News_Adapter.OnCallBackClickNews, Padcast_Adapter.OnCallBackPodcast, Category_Home_Adapter.OnCallBackClickCatHom {

    private TextView txt_profile;

    private ApiService apiService;

    Education_Adapter education_adapter;
    private RecyclerView rc_Education;

    News_Adapter news_adapter;
    private RecyclerView rc_News;

    Video_Adapter video_adapter;
    private RecyclerView rc_Video;

    Padcast_Adapter padcast_adapter;
    private RecyclerView rc_padcast;

    Category_Home_Adapter category_home_adapter;
    private RecyclerView rc_home_category;

    private TextView txt_title_1,txt_title_2,txt_title_3,txt_title_4;

    private TextView txt_more_videos;
    private TextView txt_more_news;
    private TextView txt_more_podcasts;

    private boolean doubleBackToExitPressedOnce;

    private NestedScrollView nestedScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apiService = new ApiService(this);
        FindView();
        Listener();
        get_Education();
        get_Video();
        get_News();
        get_Padcast();
        get_Category();
    }

    private void FindView()
    {
        txt_profile = findViewById(R.id.txt_go_to_profile);

        rc_Education = findViewById(R.id.rc_education);
        rc_Video = findViewById(R.id.rc_latest_videos);
        rc_News = findViewById(R.id.rc_latest_news);
        rc_padcast = findViewById(R.id.rc_latest_padcast);
        rc_home_category = findViewById(R.id.rc_home_category);

        txt_title_1 = findViewById(R.id.txt_category_1);
        txt_title_2 = findViewById(R.id.txt_category_2);
        txt_title_3 = findViewById(R.id.txt_category_3);
        txt_title_4 = findViewById(R.id.txt_category_4);

        txt_more_videos = findViewById(R.id.txt_more_videos);
        txt_more_news = findViewById(R.id.txt_more_news);
        txt_more_podcasts = findViewById(R.id.txt_more_podcast);

        nestedScroll = findViewById(R.id.nestedScroll);
    }

    private void Listener()
    {
        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile_Activity.class);
                startActivity(intent);
            }
        });
        txt_more_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), All_Video_Activity.class));
            }
        });
        txt_more_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubScribe_Activity.class));
            }
        });
        txt_more_podcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), More_Podcast_Activity.class));
            }
        });

    }

    private void get_Education()
    {
        apiService.get_Education(new Response.Listener<List<ResponseEducation>>() {
            @Override
            public void onResponse(List<ResponseEducation> response) {
                education_adapter = new Education_Adapter(response);
                rc_Education.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                rc_Education.setAdapter(education_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get_Video()
    {
        apiService.get_Videos(new Response.Listener<List<ResponseVideo>>() {
            @Override
            public void onResponse(List<ResponseVideo> response) {
                // Select Item
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(rc_Video);
                video_adapter = new Video_Adapter(response,Home_Activity.this::onClickVideo,getApplicationContext());
                // Effect
                CenterZoomLayoutManager centerZoomLayoutManager = new CenterZoomLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                rc_Video.setLayoutManager(centerZoomLayoutManager);
                rc_Video.setAdapter(video_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get_News()
    {
        apiService.get_News(new Response.Listener<List<ResponseNews>>() {
            @Override
            public void onResponse(List<ResponseNews> response) {
                news_adapter = new News_Adapter(response,getApplicationContext(),Home_Activity.this::onClickNews);
                rc_News.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rc_News.setAdapter(news_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get_Padcast()
    {
        apiService.get_Padcast(new Response.Listener<List<ResponsePadcast>>() {
            @Override
            public void onResponse(List<ResponsePadcast> response) {
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(rc_padcast);
                padcast_adapter = new Padcast_Adapter(response,getApplicationContext(),Home_Activity.this::onClickPodcast);
                rc_padcast.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                rc_padcast.setAdapter(padcast_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get_Category()
    {
        apiService.get_Home_Category(new Response.Listener<List<ResponseHomeCategory>>() {
            @Override
            public void onResponse(List<ResponseHomeCategory> response) {
                category_home_adapter = new Category_Home_Adapter(response,Home_Activity.this::onClickCatHome);
                rc_home_category.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                rc_home_category.setAdapter(category_home_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickCatHome(int id,String name)
    {
        Intent intent = new Intent(getApplicationContext(), Category_Activity.class);
        intent.putExtra(intentKey.ID_CATEGORY,id);
        intent.putExtra(intentKey.TITLE_CATEGORY,name);
        startActivity(intent);
    }

    @Override
    public void onClickVideo(String link, String title, String image)
    {
        Intent intent = new Intent(getApplicationContext(), Play_Video_Activity.class);
        intent.putExtra(LINK_VIDEO,link);
        intent.putExtra(TITLE_VIDEO,title);
        intent.putExtra(IMAGE_VIDEO,image);
        startActivity(intent);

    }

    @Override
    public void onClickNews(int id) {
        Intent intent = new Intent(getApplicationContext(), DetailsNews_Activity.class);
        intent.putExtra(ID_NEWS,id);
        startActivity(intent);
    }

    @Override
    public void onClickPodcast(int id,String title,String link,String authorName,String autherImage,String image) {
        Intent intent = new Intent(getApplicationContext(), Podcast_Activity.class);
        intent.putExtra(ID_PODCAST,id);
        intent.putExtra(TITLE_PODCAST,title);
        intent.putExtra(LINK_PODCAST,link);
        intent.putExtra(AUTHOR_PODCAST,authorName);
        intent.putExtra(AUTHOR_IMAGE_PODCAST,autherImage);
        intent.putExtra(IMAGE_PODCAST,image);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج از برنامه بکبار دیگر کلید برگشت را بزنید", Toast.LENGTH_SHORT).show();
        nestedScroll.fullScroll(View.FOCUS_UP);
        new Handler().postDelayed(new Runnable() {@Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
        }, 4000);
    }

}