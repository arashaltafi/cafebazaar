package com.arash.altafi.caffebazar.subcribe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.api.ApiService;
import com.arash.altafi.caffebazar.moreNews.More_News_Activity;
import com.arash.altafi.caffebazar.util.IabHelper;
import com.arash.altafi.caffebazar.util.IabResult;
import com.arash.altafi.caffebazar.util.Inventory;
import com.arash.altafi.caffebazar.util.Purchase;
import com.arash.altafi.caffebazar.utility.TokenContainer;
import com.arash.altafi.caffebazar.utility.intentKey;

import java.util.ArrayList;
import java.util.List;

public class SubScribe_Activity extends AppCompatActivity implements IabHelper.OnIabSetupFinishedListener,
        IabHelper.QueryInventoryFinishedListener, IabHelper.OnIabPurchaseFinishedListener, DialogSubScribe.OnCallBackSubcribe{

    public static String SUBCRIBE_30 = "su_30";
    public static String SUBCRIBE_60 = "su_60";
    public static String SUBCRIBE_90 = "su_90";

    private IabHelper iabHelper;
    private LinearLayout lnrCheck;
    private int REQUEST_SUBCRIBE = 642;
    private DialogSubScribe dialogSubScribe;
    private ApiService apiService;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_scribe);
        FindView();
        apiService = new ApiService(this);
        lnrCheck.setVisibility(View.VISIBLE);
        checkCaffeBazaar();
    }

    private void FindView()
    {
        dialogSubScribe = new DialogSubScribe();
        lnrCheck = findViewById(R.id.lnr_check);
    }

    private void checkCaffeBazaar() {
        iabHelper = new IabHelper(getApplication(), intentKey.RSS_CAFFE);
        iabHelper.startSetup(this::onIabSetupFinished);
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (result.isSuccess())
        {
            iabHelper.queryInventoryAsync(this::onQueryInventoryFinished);
        }
        else
        {
            Toast.makeText(this, "خطایی رخ داده است" , Toast.LENGTH_SHORT).show();
            lnrCheck.setVisibility(View.GONE);
            finish();
        }
    }

    private void insertSubscribe(String ref_id)
    {
        apiService = new ApiService(getApplicationContext());
        apiService.insert_Subscribe(type, ref_id,TokenContainer.getToken(), new Response.Listener<ResponseSubscribe>() {
            @Override
            public void onResponse(ResponseSubscribe response) {
                Toast.makeText(SubScribe_Activity.this, response.getMessage() , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), More_News_Activity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SubScribe_Activity.this, "خطا در اتصال" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isSuccess())
        {
            Purchase purchase30 = inv.getPurchase(SUBCRIBE_30);
            Purchase purchase60 = inv.getPurchase(SUBCRIBE_60);
            Purchase purchase90 = inv.getPurchase(SUBCRIBE_90);
            if (purchase30 == null && purchase60 == null && purchase90 == null)
            {
//                Toast.makeText(this, "شما هیچ اشتراکی ندارید" , Toast.LENGTH_SHORT).show();
                lnrCheck.setVisibility(View.GONE);
                dialogSubScribe.show(getSupportFragmentManager(),null);
            }
            else
            {
                lnrCheck.setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(), More_News_Activity.class));
                finish();
            }
        }
    }

    private void purchaseProduct(String subscribe)
    {
        iabHelper.launchPurchaseFlow(this,subscribe,REQUEST_SUBCRIBE,this::onIabPurchaseFinished);
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isSuccess())
        {
            if (info != null)
            {
                insertSubscribe(info.getOrderId());
            }
        }
        else if(result.isFailure())
        {
            Toast.makeText(this, "اشتراکی را انتخاب نکرده اید" , Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogSubScribe.show(getSupportFragmentManager(),null);
                }
            },300);
        }
    }

    @Override
    public void onClickSubcribe(String subcribe) {
        purchaseProduct(subcribe);
        type = subcribe;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SUBCRIBE)
        {
            iabHelper.handleActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iabHelper != null) {
            iabHelper.dispose();
            iabHelper = null;
        }
    }

}