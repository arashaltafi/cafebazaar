package com.arash.altafi.caffebazar.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.splash.Splash_Activity;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.google.android.material.button.MaterialButton;

public class Login_Activity extends AppCompatActivity {

    MaterialButton btn_login;
    EditText edt_login;
    private ApiService apiService;
    ProgressBar pr_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkToken();

        apiService = new ApiService(this);
        FindView();
        Listener();
    }

    private void FindView() {
        btn_login = findViewById(R.id.btn_login);
        edt_login = findViewById(R.id.edt_login);
        pr_loading = findViewById(R.id.pr_loading);
    }

    private void Listener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edt_login.getText().toString().trim();
                if (phone.equals("")) {
                    edt_login.setError("لطفا شماره را وارد نمایید");
                } else if (!phone.startsWith("09")) {
                    edt_login.setError("لطفا شماره موبایل را به طور صحیح و با 09 وارد نمایید");
                } else if (phone.length() < 11) {
                    edt_login.setError("لطفا شماره را به طور صحیح وارد نمایید");
                } else {
                    pr_loading.setVisibility(View.VISIBLE);
                    btn_login.setVisibility(View.GONE);
                    apiService.login(phone, phone, new Response.Listener<ResponseLogin>() {
                        @Override
                        public void onResponse(ResponseLogin response) {
                            if (response.isIsRegister()) {
                                intentVerify(response.getToken(), response.getCode());
                                pr_loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login_Activity.this, error.getMessage() , Toast.LENGTH_LONG).show();
//                            Toast.makeText(Login_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
                            pr_loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void intentVerify(String token, String code) {
        Intent intent = new Intent(getApplicationContext(), Verify_Activity.class);
        intent.putExtra("token", token);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    private void checkToken()
    {
        if (TokenContainer.getToken() != null)
        {
            startActivity(new Intent(getApplicationContext(), Splash_Activity.class));
            finish();
        }
    }

}