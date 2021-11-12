package com.arash.altafi.caffebazar.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.about.About_Activity;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.favourite.Favourite_Activity;
import com.arash.altafi.caffebazar.transaction.Transaction_Activity;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Activity extends AppCompatActivity {

    private ImageView imgBack;
    private CircleImageView imgProfile;
    private TextView txtProfile;
    private TextView txtCommentCount,txtCourseCount;
    private MaterialButton btnEditProfile;
    private CoordinatorLayout coordinatorLayout;

    private ApiService apiService;

    private String image,name;

    private RelativeLayout rltv_expire,rltv_Transaction,rltv_fav,rltv_contact;

    private String expire;

    private RelativeLayout rltv_loading_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        apiService = new ApiService(this);

        FindView();
        Listener();
//        setData();
    }

    private void FindView()
    {
        coordinatorLayout = findViewById(R.id.coordinator_profile);
        imgBack = findViewById(R.id.img_back);
        txtProfile = findViewById(R.id.txt_profile);
        txtCourseCount = findViewById(R.id.txt_course_count);
        txtCommentCount = findViewById(R.id.txt_comment_count);
        imgProfile = findViewById(R.id.img_profile);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        rltv_expire = findViewById(R.id.rltv_expire);
        rltv_Transaction = findViewById(R.id.rltv_Transaction);
        rltv_loading_profile = findViewById(R.id.rltv_loading_profile);
        rltv_fav = findViewById(R.id.rltv_fav);
        rltv_contact = findViewById(R.id.rltv_contact);
    }

    private void Listener()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditProfile_Activity.class);
                intent.putExtra(intentKey.NAME_PROFILE,name);
                intent.putExtra(intentKey.IMAGE_PROFILE,image);
                startActivity(intent);
            }
        });
        rltv_expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogExpireDate dialogExpireDate = DialogExpireDate.newInstance(expire);
                dialogExpireDate.show(getSupportFragmentManager(),null);
            }
        });
        rltv_Transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Transaction_Activity.class));
            }
        });
        rltv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Favourite_Activity.class));
            }
        });
        rltv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), About_Activity.class));
            }
        });
    }

    private void setData()
    {
        apiService.get_Profile(TokenContainer.getToken(), new Response.Listener<ResponseProfile>() {
            @Override
            public void onResponse(ResponseProfile response) {
                Glide.with(getApplicationContext()).load(response.getImage()).into(imgProfile);
                txtProfile.setText(response.getNameFamily().toString().trim());
                name = response.getNameFamily().toString().trim();
                image = response.getImage();
                txtCourseCount.setText(String.valueOf(response.getTransactionCount()));
                txtCommentCount.setText(String.valueOf(response.getCommentCount()));
                expire = response.getExpireDate();
                rltv_loading_profile.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout, "خطا در اتصال" ,Snackbar.LENGTH_SHORT).show();
                rltv_loading_profile.setVisibility(View.GONE);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }
}