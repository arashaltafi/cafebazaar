package com.arash.altafi.caffebazar.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.link.Link_Activity;
import com.arash.altafi.caffebazar.utility.ConvertImage;
import com.arash.altafi.caffebazar.utility.RuntimePermissionsActivity;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile_Activity extends RuntimePermissionsActivity {

    private ImageView imgBack;
    private CircleImageView imgUser;
    private MaterialButton btnEdit;
    private CoordinatorLayout coordinatorLayout;
    private TextInputEditText edtName;
    private ProgressBar progressBar;

    private String name,image;

    private ApiService apiService;

    private final int REQUEST_GALLERY = 8585;
    private Bitmap bitmap;

    private ConvertImage convertImage;
    private String imageEncode = "";

    private final int REQUEST_READ_EXTERNAL_STORAGE = 234342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        apiService = new ApiService(this);
        convertImage = new ConvertImage();

        FindView();
        Listener();
        get_Intent();
    }

    private void get_permision()
    {
        EditProfile_Activity.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE)
        {
            getPics();
        }
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
        Toast.makeText(this, "شما مجوز دسترسی را تایید نکرده اید" , Toast.LENGTH_SHORT).show();
    }

    private void get_Intent()
    {
        name = getIntent().getStringExtra(intentKey.NAME_PROFILE);
        image = getIntent().getStringExtra(intentKey.IMAGE_PROFILE);
        edtName.setText(name);
        Glide.with(getApplicationContext()).load(image).into(imgUser);
    }

    private void FindView()
    {
        imgBack = findViewById(R.id.img_back);
        imgUser = findViewById(R.id.img_edit_user);
        btnEdit = findViewById(R.id.btn_edit);
        edtName = findViewById(R.id.edt_name_family);
        coordinatorLayout = findViewById(R.id.coordinator_edit_profile);
        progressBar = findViewById(R.id.pr_loading_edit);
    }

    private void Listener()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_permision();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnEdit.getText().toString().trim().equals(null))
                {
                    edtName.setError("لطفا نام خود را وارد نمایید");
                }
                else
                {
                    edit_prodile();
                }
            }
        });
    }

    private void edit_prodile()
    {
        progressBar.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.GONE);
        apiService.edit_Profile(edtName.getText().toString().trim(), imageEncode , TokenContainer.getToken(), new Response.Listener<ResponseEditProfile>() {
            @Override
            public void onResponse(ResponseEditProfile response) {
                progressBar.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                Snackbar.make(coordinatorLayout, response.getMessage().toString() ,Snackbar.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                Snackbar.make(coordinatorLayout, "خطا در اتصال" ,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getPics()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgUser.setImageBitmap(bitmap);
                imageEncode = convertImage.convertTostring(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}