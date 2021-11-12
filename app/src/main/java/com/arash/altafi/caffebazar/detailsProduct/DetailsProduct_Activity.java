package com.arash.altafi.caffebazar.detailsProduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.arash.altafi.caffebazar.profile.Profile_Activity;
import com.arash.altafi.caffebazar.util.IabHelper;
import com.arash.altafi.caffebazar.util.IabResult;
import com.arash.altafi.caffebazar.util.Inventory;
import com.arash.altafi.caffebazar.util.Purchase;
import com.arash.altafi.caffebazar.utility.PriceConverter;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsProduct_Activity extends AppCompatActivity implements IabHelper.OnIabSetupFinishedListener,
        IabHelper.QueryInventoryFinishedListener, IabHelper.OnIabPurchaseFinishedListener {

    private TextView txtTitle, txtAuther, txtTime, txtCount, txtPrice, txtContent;
    private ImageView imgProduct, imgBack;
    private CircleImageView imgAuther;
    private MaterialButton btnBuyProduct;
    private ProgressBar prLoading;
    private RelativeLayout rltvLoading;
    private ApiService apiService;
    private int id_product;
    private IabHelper iabHelper;
    private String SKU_PRODUCT;
    private int REQUEST_BUY_PRODUCT = 4561;
    private boolean isCheckBuyProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        apiService = new ApiService(this);
        FindView();
        Listener();
        set_DataProduct();
    }

    private void FindView() {
        txtAuther = findViewById(R.id.txt_author_details_product);
        txtContent = findViewById(R.id.txt_content_details_product);
        txtCount = findViewById(R.id.txt_count_details_product);
        txtPrice = findViewById(R.id.txt_price_details_product);
        txtTime = findViewById(R.id.txt_time_details_product);
        txtTitle = findViewById(R.id.txt_title_details_product);
        imgAuther = findViewById(R.id.img_author_details_product);
        imgProduct = findViewById(R.id.img_details_product);
        imgBack = findViewById(R.id.img_back);
        btnBuyProduct = findViewById(R.id.btn_buy_details_product);
        prLoading = findViewById(R.id.pr_loading_caffebazaar);
        rltvLoading = findViewById(R.id.rltv_loading_data);
    }

    private void Listener() {
        id_product = getIntent().getIntExtra(intentKey.ID_PRODUCT, 1);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckBuyProduct) {
                    startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
                } else {
                    prLoading.setVisibility(View.VISIBLE);
                    btnBuyProduct.setVisibility(View.GONE);
                    checkCaffeBazaar();
                }
            }
        });
    }

    private void set_DataProduct() {
        apiService.get_details_product(id_product, TokenContainer.getToken(), new Response.Listener<ResponseDetailsProduct>() {
            @Override
            public void onResponse(ResponseDetailsProduct response) {
                isCheckBuyProduct = response.isStatus();
                if (isCheckBuyProduct) {
                    btnBuyProduct.setText("دانشجوی دوره");
                } else {

                }
                setResponse(response);
                rltvLoading.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessage("خطا در اتصال");
                rltvLoading.setVisibility(View.GONE);
                finish();
            }
        });
    }

    private void setResponse(ResponseDetailsProduct response) {
        txtTitle.setText(response.getTitle());
        txtTime.setText(response.getTime());
        txtAuther.setText(response.getTeacherName());
        txtContent.setText(response.getContent());
        txtCount.setText(response.getSellsNumber());
        txtPrice.setText(PriceConverter.priceConvert(response.getPrice()));
        Glide.with(getApplicationContext()).load(response.getTeacherImage()).into(imgAuther);
        Glide.with(getApplicationContext()).load(response.getImage()).into(imgProduct);
        SKU_PRODUCT = response.getSku();
    }

    private void checkCaffeBazaar() {
        iabHelper = new IabHelper(getApplication(), intentKey.RSS_CAFFE);
        iabHelper.startSetup(this::onIabSetupFinished);
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (result.isSuccess()) {
            List<String> product = new ArrayList<>();
            product.add(SKU_PRODUCT);
            iabHelper.queryInventoryAsync(true, product, this::onQueryInventoryFinished);
        } else {
            showMessage("خطایی رخ داده است");
            prLoading.setVisibility(View.GONE);
            btnBuyProduct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isSuccess()) {
            Purchase purchase = inv.getPurchase(SKU_PRODUCT);
            if (purchase == null) {
                purchaseProduct();
            } else {
                showMessage("این محصول قبلا خریداری شده است");
                prLoading.setVisibility(View.GONE);
                btnBuyProduct.setVisibility(View.VISIBLE);
            }
        }
    }

    private void purchaseProduct() {
        iabHelper.launchPurchaseFlow(this, SKU_PRODUCT, REQUEST_BUY_PRODUCT, this::onIabPurchaseFinished);
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isSuccess()) {
            if (info != null) {
                insert_Transaction(info.getOrderId());
                prLoading.setVisibility(View.GONE);
                btnBuyProduct.setVisibility(View.VISIBLE);
            }
        } else if (result.isFailure()) {
            showMessage("تلاش ناموفق");
            prLoading.setVisibility(View.GONE);
            btnBuyProduct.setVisibility(View.VISIBLE);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_BUY_PRODUCT) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void insert_Transaction(String ref_Id) {
        apiService = new ApiService(getApplicationContext());
        apiService.insert_Transaction(id_product, ref_Id, TokenContainer.getToken(), new Response.Listener<ResponseTransaction>() {
            @Override
            public void onResponse(ResponseTransaction response) {
                showMessage(response.getMessage());
                txtCount.setText(String.valueOf(response.getCount()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessage("خطا در اتصال");
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iabHelper != null) {
            iabHelper.dispose();
            iabHelper = null;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}