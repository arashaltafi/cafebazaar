package com.arash.altafi.caffebazar.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.arash.altafi.caffebazar.detailsProduct.DetailsProduct_Activity;

import java.util.List;

import static com.arash.altafi.caffebazar.utility.intentKey.ID_PRODUCT;
import static com.arash.altafi.caffebazar.utility.intentKey.TITLE_CATEGORY;

public class Category_Activity extends AppCompatActivity implements Category_Adapter.OnCallBackProduct {

    private ApiService apiService;
    private int category;
    private String title_category;

    private ImageView img_back;
    private TextView txt_title;

    private Category_Adapter category_adapter;
    private RecyclerView rc_category;

    private RelativeLayout rltv_pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category = getIntent().getIntExtra(intentKey.ID_CATEGORY,1);
        title_category = getIntent().getStringExtra(TITLE_CATEGORY);
        apiService = new ApiService(this);
        get_Category();
        FindView();
        Listener();
    }

    private void FindView()
    {
        rc_category = findViewById(R.id.rc_category);
        img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_title_activity);
        rltv_pr = findViewById(R.id.rltv_pr_category);
    }

    private void Listener()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_title.setText(title_category);
    }

    private void get_Category()
    {
        apiService.category(category, new Response.Listener<List<ResponseCategory>>() {
            @Override
            public void onResponse(List<ResponseCategory> response) {
                category_adapter = new Category_Adapter(response,Category_Activity.this::onClickProduct,getApplicationContext());
                rc_category.setAdapter(category_adapter);
                rc_category.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                rltv_pr.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Category_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
                rltv_pr.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClickProduct(int id)
    {
        Intent intent = new Intent(getApplicationContext(), DetailsProduct_Activity.class);
        intent.putExtra(ID_PRODUCT,id);
        startActivity(intent);
    }
}