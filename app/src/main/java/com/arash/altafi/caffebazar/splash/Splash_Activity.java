package com.arash.altafi.caffebazar.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.Home_Activity;

/**
 * Programming by arash altafi
 * Github : arashaltafi
 * test
 * test 2
*/

public class Splash_Activity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView txt_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FindView();
        Listener();
    }

    private void FindView()
    {
        lottieAnimationView = findViewById(R.id.lottie_view);
        txt_version = findViewById(R.id.txt_version);
    }

    private void Listener()
    {
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                finish();
            }
        },3000);

        txt_version.setText("نسخه :" + " " + BuildConfig.VERSION_NAME);
    }

}