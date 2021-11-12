package com.arash.altafi.caffebazar.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.splash.Splash_Activity;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.UserInfoManager;
import com.google.android.material.button.MaterialButton;
import com.mukesh.OtpView;

public class Verify_Activity extends AppCompatActivity {

    OtpView edt_verify;
    MaterialButton btn_verify;
    private String code;
    private String token;
    ProgressBar pr_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        FindView();
        get_Intent();
        Listener();

    }

    private void FindView()
    {
        edt_verify = findViewById(R.id.edt_verify);
        btn_verify = findViewById(R.id.btn_verify);
        pr_verify = findViewById(R.id.pr_verify);
    }

    private void get_Intent()
    {
        code = getIntent().getStringExtra("code");
        token = getIntent().getStringExtra("token");
    }

    private void Listener()
    {
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verify = edt_verify.getText().toString().trim();
                if (verify.equals(code))
                {
                    pr_verify.setVisibility(View.VISIBLE);
                    btn_verify.setVisibility(View.GONE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), Splash_Activity.class));
                            finish();
                            UserInfoManager userInfoManager = new UserInfoManager(getApplicationContext());
                            userInfoManager.saveInfo(token);
                            TokenContainer.updateToken(token);
                        }
                    },1000);
                }
                else
                {
                    pr_verify.setVisibility(View.VISIBLE);
                    btn_verify.setVisibility(View.GONE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Verify_Activity.this, "کد وارد شده نامتعبر است" , Toast.LENGTH_SHORT).show();
                            pr_verify.setVisibility(View.GONE);
                            btn_verify.setVisibility(View.VISIBLE);
                        }
                    },1000);
                }
            }
        });
    }

}