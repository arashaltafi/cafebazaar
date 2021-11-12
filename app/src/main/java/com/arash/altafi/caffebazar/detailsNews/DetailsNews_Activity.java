package com.arash.altafi.caffebazar.detailsNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.comment.Comment_Activity;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsNews_Activity extends AppCompatActivity {

    private TextView txtTitle,txtAuther,txtContent,txtDate;
    private ImageView imgNews,imgBack,img_Favourite;
    private CircleImageView imgAuther;
    private RelativeLayout rltvLoading;
    private ApiService apiService;
    private int id_news;
    private FloatingActionButton fabComment;
    private CoordinatorLayout coordinatorLayout;
    private boolean isCheckFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        apiService = new ApiService(this);
        FindView();
        Listener();
        set_DataNews();
    }

    private void FindView()
    {
        id_news = getIntent().getIntExtra(intentKey.ID_NEWS,1);
        txtAuther = findViewById(R.id.txt_author_details_news);
        txtContent = findViewById(R.id.txt_content_details_news);
        txtTitle = findViewById(R.id.txt_title_details_news);
        txtDate = findViewById(R.id.txt_date_news);
        imgAuther = findViewById(R.id.img_author_details_news);
        imgNews = findViewById(R.id.img_details_news);
        img_Favourite = findViewById(R.id.img_favourite);
        imgBack = findViewById(R.id.img_back);
        rltvLoading = findViewById(R.id.rltv_loading_data);
        fabComment = findViewById(R.id.fab_comment);
        coordinatorLayout = findViewById(R.id.coordinatorLayout_news);
    }

    private void Listener()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Comment_Activity.class);
                intent.putExtra(intentKey.ID_NEWS,id_news);
                startActivity(intent);
            }
        });
        img_Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_Favourite();
            }
        });
    }

    private void get_Favourite()
    {
        if (isCheckFavourite)
        {
            apiService.insert_Favourite(id_news, "true", TokenContainer.getToken(), new Response.Listener<ResponseMessageFavourite>() {
                @Override
                public void onResponse(ResponseMessageFavourite response) {
                    Snackbar.make(coordinatorLayout, response.getMessage() ,Snackbar.LENGTH_LONG).show();
                    img_Favourite.setImageResource(R.drawable.ic_favorite);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(coordinatorLayout, "خطا در اتصال" ,Snackbar.LENGTH_SHORT).show();
                }
            });
            isCheckFavourite = false;
        }
        else
        {
            apiService.insert_Favourite(id_news, "false", TokenContainer.getToken(), new Response.Listener<ResponseMessageFavourite>() {
                @Override
                public void onResponse(ResponseMessageFavourite response) {
                    Snackbar.make(coordinatorLayout, response.getMessage() ,Snackbar.LENGTH_LONG).show();
                    img_Favourite.setImageResource(R.drawable.ic_favourite);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(coordinatorLayout, "خطا در اتصال" ,Snackbar.LENGTH_SHORT).show();
                }
            });
            isCheckFavourite = true;
        }

    }

    private void set_DataNews()
    {
        apiService.get_details_news(id_news, TokenContainer.getToken(), new Response.Listener<ResponseDetailsNews>() {
            @Override
            public void onResponse(ResponseDetailsNews response) {
                isCheckFavourite = response.isStatus();
                if (response.isStatus())
                {
                    img_Favourite.setImageResource(R.drawable.ic_favourite);
                }
                else
                {
                    img_Favourite.setImageResource(R.drawable.ic_favorite);
                }
                setResponse(response);
                rltvLoading.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout, "خطا در اتصال" ,Snackbar.LENGTH_SHORT).show();
                rltvLoading.setVisibility(View.GONE);
                finish();
            }
        });
    }

    private void setResponse(ResponseDetailsNews response)
    {
        txtDate.setText(response.getDate());
        txtAuther.setText(response.getName());
        txtContent.setText(response.getContent());
        txtTitle.setText(response.getTitle());
        Glide.with(this).load(response.getWriterImage()).into(imgAuther);
        Glide.with(this).load(response.getImage()).into(imgNews);
    }

}