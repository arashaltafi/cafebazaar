package com.arash.altafi.caffebazar.comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Comment_Activity extends AppCompatActivity {

    private CoordinatorLayout coordinator_Comment;
    private ImageView img_Send;
    private EditText edt_Comment;
    private RecyclerView rc_Comment;
    private ApiService apiService;
    int id;
    private ProgressBar pr_loading_comment;
    private Comment_Adapter comment_adapter;
    private ImageView imgBack;
    private LottieAnimationView lottieAnimationView;
    private RelativeLayout rltv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        apiService = new ApiService(this);
        id = getIntent().getIntExtra(intentKey.ID_NEWS,1);
        FindView();
        Listener();
        get_comment();
    }

    private void FindView()
    {
        coordinator_Comment = findViewById(R.id.coordinator_comment);
        img_Send = findViewById(R.id.img_send_comment);
        edt_Comment = findViewById(R.id.edt_comment);
        rc_Comment = findViewById(R.id.rc_comment);
        pr_loading_comment = findViewById(R.id.pr_loading_comment);
        imgBack = findViewById(R.id.img_back);
        lottieAnimationView = findViewById(R.id.lottie_empty_comment);
        rltv_empty = findViewById(R.id.rltv_lottie_empty);
    }

    private void Listener()
    {
        img_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_Comment.getText().toString().trim().equals(""))
                {
                    edt_Comment.setError("لطفا دیدگاه خود را وارد نمایید");
                    edt_Comment.setText("");
                }
                else
                {
                    send_comment();
                    pr_loading_comment.setVisibility(View.VISIBLE);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void send_comment()
    {
        apiService.send_comment(id, edt_Comment.getText().toString().trim() , TokenContainer.getToken(), new Response.Listener<ResponseMessageComment>() {
            @Override
            public void onResponse(ResponseMessageComment response) {
                Snackbar.make(coordinator_Comment,response.getMessage(),Snackbar.LENGTH_LONG).show();
                edt_Comment.setText("");
                pr_loading_comment.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinator_Comment,"خطا در اتصال",Snackbar.LENGTH_LONG).show();
                pr_loading_comment.setVisibility(View.GONE);
            }
        });
    }

    private void get_comment()
    {
        apiService.get_comment(id, new Response.Listener<List<ResponseComment>>() {
            @Override
            public void onResponse(List<ResponseComment> response) {
                if (response.isEmpty())
                {
                    rltv_empty.setVisibility(View.VISIBLE);
                }
                else
                {
                    rltv_empty.setVisibility(View.GONE);
                    comment_adapter = new Comment_Adapter(response,getApplicationContext());
                    rc_Comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rc_Comment.setAdapter(comment_adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinator_Comment,"خطا در اتصال",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}