package com.arash.altafi.caffebazar.link;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
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
import com.arash.altafi.caffebazar.playVideo.Play_Video_Activity;
import com.arash.altafi.caffebazar.utility.RuntimePermissionsActivity;
import com.arash.altafi.caffebazar.utility.SharedPreferences_Download;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import ir.siaray.downloadmanagerplus.classes.Downloader;
import ir.siaray.downloadmanagerplus.enums.Storage;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;

public class Link_Activity extends RuntimePermissionsActivity implements Link_Adapter.OnClickPlayLink , Link_Adapter.Download {

    private ImageView imgBack;
    private RecyclerView rc_link;
    private ApiService apiService;
    private Link_Adapter link_adapter;
    private ProgressBar pr_link;
    private LottieAnimationView lottieAnimationView;
    private RelativeLayout rltv_empty;
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 17;

    public static boolean ok1 = false;
    private SharedPreferences_Download sharedPreferences_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        apiService = new ApiService(this);
        FindView();
        Listener();
        get_Data(getIntent().getStringExtra("link"));
//        get_permision();
        sharedPreferences_download = new SharedPreferences_Download(this);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE)
        {
            if (sharedPreferences_download.isFirst())
            {
                Toast.makeText(this, "مجوز تایید شد", Toast.LENGTH_SHORT).show();
            }
            sharedPreferences_download.saveDownloadInfo(false);
            ok1 = true;
        }
        else
        {
            ok1 = false;
        }
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
        Toast.makeText(this, "شما مجوز دسترسی را تایید نکرده اید" , Toast.LENGTH_SHORT).show();
    }

    private void get_permision()
    {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                }).check();
    }

    private void FindView()
    {
        imgBack = findViewById(R.id.img_back);
        rc_link = findViewById(R.id.rc_link);
        pr_link = findViewById(R.id.pr_link);
        lottieAnimationView = findViewById(R.id.lottie_empty_comment);
        rltv_empty = findViewById(R.id.rltv_lottie_empty);
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

    private void get_Data(String ref_id)
    {
        apiService.get_Link(ref_id, TokenContainer.getToken(), new Response.Listener<List<ResponseLink>>() {
            @Override
            public void onResponse(List<ResponseLink> response) {
                if (response.isEmpty())
                {
                    rltv_empty.setVisibility(View.VISIBLE);
                }
                else
                {
                    rltv_empty.setVisibility(View.GONE);
                    link_adapter = new Link_Adapter(response,getApplicationContext(),Link_Activity.this::onClickPlay,Link_Activity.this::download);
                    rc_link.setAdapter(link_adapter);
                    rc_link.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                    decoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.shape_devider));
                    rc_link.addItemDecoration(decoration);
                }
                pr_link.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Link_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
                pr_link.setVisibility(View.GONE);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClickPlay(String link, String title, String image) {
        Intent intent = new Intent(getApplicationContext(), Play_Video_Activity.class);
        intent.putExtra(intentKey.TITLE_VIDEO,title);
        intent.putExtra(intentKey.LINK_VIDEO,link);
        intent.putExtra(intentKey.IMAGE_VIDEO,image);
        startActivity(intent);
    }

    @Override
    public void download() {
        Link_Activity.super.requestAppPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE);
    }
}