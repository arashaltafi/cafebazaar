package com.arash.altafi.caffebazar.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.UtilsSocial;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class About_Activity extends AppCompatActivity {

    private ApiService apiService;
    private ImageView imgBack;
    private CoordinatorLayout coordinatorLayout;
    private MaterialButton btnSupport;
    private TextInputEditText edtSupport;
    private ProgressBar progressBar;
    private ImageView img_instagram,img_telegram,img_whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        apiService = new ApiService(this);
        FindView();
        Listener();
    }

    private void FindView()
    {
        imgBack = findViewById(R.id.img_back);
        coordinatorLayout = findViewById(R.id.coordinator);
        btnSupport = findViewById(R.id.btn_support);
        edtSupport = findViewById(R.id.edt_support);
        progressBar = findViewById(R.id.progress_bar);
        img_instagram = findViewById(R.id.img_Instagram);
        img_whatsapp = findViewById(R.id.img_Whatsapp);
        img_telegram = findViewById(R.id.img_Telegram);
    }

    private void Listener()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = edtSupport.getText().toString().trim();
                if (mess.isEmpty())
                {
                    edtSupport.setError("لطفا پیام خود را وارد نمایید");
                }
                else
                {
                    send_data(mess);
                }
            }
        });
        img_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsSocial.instagram(getApplicationContext(),"arashaltafi");
            }
        });
        img_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsSocial.telegram(getApplicationContext(),"arashaltafi1377");
            }
        });
        img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsSocial.whatsApp(getApplicationContext(),"+989187677641");
            }
        });
    }

    private void send_data(String message)
    {
        progressBar.setVisibility(View.VISIBLE);
        btnSupport.setVisibility(View.GONE);
        apiService.form(message, TokenContainer.getToken(), new Response.Listener<ResponseMessageForm>() {
            @Override
            public void onResponse(ResponseMessageForm response) {
                Snackbar.make(coordinatorLayout, response.getMessage() ,Snackbar.LENGTH_LONG).show();
                edtSupport.setText("");
                progressBar.setVisibility(View.GONE);
                btnSupport.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout, "خطا در اتصال" ,Snackbar.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnSupport.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}