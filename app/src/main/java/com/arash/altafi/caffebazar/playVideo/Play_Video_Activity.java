package com.arash.altafi.caffebazar.playVideo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.animation.content.Content;
import com.arash.altafi.caffebazar.R;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import static com.arash.altafi.caffebazar.utility.intentKey.IMAGE_VIDEO;
import static com.arash.altafi.caffebazar.utility.intentKey.LINK_VIDEO;
import static com.arash.altafi.caffebazar.utility.intentKey.TITLE_VIDEO;

public class Play_Video_Activity extends AppCompatActivity implements Player.EventListener{

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private ProgressBar pr_loading;
    private ImageView img_loading;
    private TextView txt_title;
    private String link,title,image;
    private boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__video);

        FindView();
        Get_Intent();
        Listener();
        player = new SimpleExoPlayer.Builder(this).build();
        set_Video(link);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            hideSystemUI();
//        }
//    }
//
//    private void hideSystemUI()
//    {
//        View decorView = getWindow().getDecorView();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
//        }
//        playerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSystemUI();
//                click = false;
//            }
//        });
//    }
//
//    private void showSystemUI()
//    {
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hideSystemUI();
//            }
//        },1000);
//        if (!click)
//        {
//            playerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    hideSystemUI();
//                    click = true;
//                }
//            });
//        }
//
//    }

    private void FindView()
    {
        playerView = findViewById(R.id.player_View);
        txt_title = findViewById(R.id.txt_title_video_play);
        img_loading = findViewById(R.id.img_video_play);
        pr_loading = findViewById(R.id.pr_loading_video);
    }

    private void Get_Intent()
    {
        link = getIntent().getStringExtra(LINK_VIDEO);
        title = getIntent().getStringExtra(TITLE_VIDEO);
        image = getIntent().getStringExtra(IMAGE_VIDEO);
    }

    private void Listener()
    {
        Glide.with(getApplicationContext()).load(image).into(img_loading);
        txt_title.setText(title);
    }

    private void set_Video(String linkk)
    {
        // Create a data source factory.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),Util.getUserAgent(getApplicationContext(),getString(R.string.app_name)));
        // Create a progressive media source pointing to a stream uri.
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(linkk));
        // Set the media source to be played.
        player.setMediaSource(mediaSource);
        // Prepare the player.
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(this);
        playerView.setPlayer(player);
    }

    @Override
    public void onPlaybackStateChanged(int state) {
        switch (state)
        {
            case Player.STATE_BUFFERING:
                pr_loading.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_READY:
                pr_loading.setVisibility(View.GONE);
                img_loading.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        player.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == 1)
        {
//            Toast.makeText(this, "PORTRAIT" , Toast.LENGTH_SHORT).show();
            txt_title.setVisibility(View.VISIBLE);
//            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        }
        else if (orientation == 2)
        {
//            Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
            txt_title.setVisibility(View.GONE);
//            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player.play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.pause();
    }
}