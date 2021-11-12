package com.arash.altafi.caffebazar.detailsPodcast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.utility.Utilities;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class Podcast_Activity extends AppCompatActivity {

    private CircleImageView imgAuthor;
    private TextView txtAuthor;
    private ImageView imgPodcast;
    private TextView txtPosition;
    private TextView txtDuration;
    private TextView txtTitle;
    private SeekBar seekBar;
    private ImageView imgBackward;
    private ImageView imgForward;
    private ImageView imgPlay;
    private RelativeLayout rltvPodcast;
    //
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private Utilities utilities;
    private int id;
    private String title,link,autherName,image,authorImage;
//    private ImageView img_blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        get_intent();
        utilities = new Utilities();
        FindView();
        listener();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rltvPodcast.setVisibility(View.GONE);
                get_intent_data();
            }
        },500);

    }

    private void FindView()
    {
        imgAuthor = findViewById(R.id.img_auther_podcast);
        txtAuthor = findViewById(R.id.txt_auther_podcast);
        txtTitle = findViewById(R.id.txt_title_podcast);
        imgPodcast = findViewById(R.id.img_podcast);
        txtPosition = findViewById(R.id.txt_position_podcast);
        txtDuration = findViewById(R.id.txt_duration_podcast);
        seekBar = findViewById(R.id.seekbar_music);
        imgBackward = findViewById(R.id.img_backward);
        imgForward = findViewById(R.id.img_forward);
        imgPlay = findViewById(R.id.img_Play);
        rltvPodcast = findViewById(R.id.rltv_loading_podcast);
//        img_blur = findViewById(R.id.img_blur);
    }

    private void get_intent()
    {
        id = getIntent().getIntExtra(intentKey.ID_PODCAST,1);
        title = getIntent().getStringExtra(intentKey.TITLE_PODCAST);
        link = getIntent().getStringExtra(intentKey.LINK_PODCAST);
        autherName = getIntent().getStringExtra(intentKey.AUTHOR_PODCAST);
        image = getIntent().getStringExtra(intentKey.IMAGE_PODCAST);
        authorImage = getIntent().getStringExtra(intentKey.AUTHOR_IMAGE_PODCAST);
    }

    private void get_intent_data()
    {
        txtAuthor.setText(autherName);
        txtTitle.setText(title);

        // تار کردن عکس
//        Picasso.with(getApplicationContext()).load(image).transform(new BlurTransformation(getApplicationContext())).into(img_blur);

        Glide.with(getApplicationContext()).load(authorImage).into(imgAuthor);
        Glide.with(getApplicationContext()).load(image).into(imgPodcast);
        Podcast_Run(link);
    }

    private void Podcast_Run(String link)
    {
        mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(link));
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtPosition.setText(utilities.milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                                txtDuration.setText(utilities.milliSecondsToTimer(mediaPlayer.getDuration()));
                                int progress = (int) utilities.getProgressPercentage(mediaPlayer.getCurrentPosition(),mediaPlayer.getDuration());
                                seekBar.setProgress(progress);
                                mediaPlayer.setLooping(false);
                            }
                        });
                    }
                },1000,1000);
            }
        });
    }

    private void listener()
    {
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.plays);
                }
                else
                {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.pauses);
                }
            }
        });
        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTime = mediaPlayer.getCurrentPosition();
                if (currentTime + 5000 <= mediaPlayer.getDuration())
                {
                    mediaPlayer.seekTo(currentTime + 5000);
                }
                else
                {
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });
        imgBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTime = mediaPlayer.getCurrentPosition();
                if (currentTime - 5000 >= 0)
                {
                    mediaPlayer.seekTo(currentTime - 5000);
                }
                else
                {
                    mediaPlayer.seekTo(0);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mediaPlayer.release();
        mediaPlayer=null;
    }

}