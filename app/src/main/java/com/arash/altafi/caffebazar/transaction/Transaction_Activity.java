package com.arash.altafi.caffebazar.transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.link.Link_Activity;
import com.arash.altafi.caffebazar.utility.TokenContainer;

import java.util.List;

public class Transaction_Activity extends AppCompatActivity implements GetTransaction_Adapter.onClickRefId {

    private ImageView img_Back;
    private RecyclerView rc_transaction;
    private ApiService apiService;
    private GetTransaction_Adapter getTransaction_adapter;
    private ProgressBar pr_transaction;
    private LottieAnimationView lottieAnimationView;
    private RelativeLayout rltv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        apiService = new ApiService(this);
        FindView();
        Listener();
        setData();
    }

    private void FindView()
    {
        img_Back = findViewById(R.id.img_back);
        rc_transaction = findViewById(R.id.rc_transaction);
        pr_transaction = findViewById(R.id.pr_transaction);
        lottieAnimationView = findViewById(R.id.lottie_empty_comment);
        rltv_empty = findViewById(R.id.rltv_lottie_empty);
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

    private void setData()
    {
        apiService.get_Transaction(TokenContainer.getToken(), new Response.Listener<List<ResponseGetTransaction>>() {
            @Override
            public void onResponse(List<ResponseGetTransaction> response) {
                if (response.isEmpty())
                {
                    rltv_empty.setVisibility(View.VISIBLE);
                }
                else
                {
                    rltv_empty.setVisibility(View.GONE);
                    getTransaction_adapter = new GetTransaction_Adapter(response,Transaction_Activity.this::onClickRef);
                    rc_transaction.setAdapter(getTransaction_adapter);
                    rc_transaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
                pr_transaction.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Transaction_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
                pr_transaction.setVisibility(View.GONE);
                finish();
            }
        });
    }

    @Override
    public void onClickRef(String ref) {
        Intent intent = new Intent(getApplicationContext(), Link_Activity.class);
        intent.putExtra("link",ref);
        startActivity(intent);
    }
}