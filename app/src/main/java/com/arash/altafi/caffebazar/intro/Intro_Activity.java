package com.arash.altafi.caffebazar.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.auth.Login_Activity;
import com.arash.altafi.caffebazar.utility.SharedPreferences_Intro;

import java.util.ArrayList;
import java.util.List;

public class Intro_Activity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private LinearLayout lnrIndicator;
    private ImageView imgPrev;
    private ImageView imgForward;
    private ImageView imgDone;

    private Intro_Adapter intro_adapter;
    private List<Intro_Model> intro_models = new ArrayList<>();

    private SharedPreferences_Intro preferences_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences_intro = new SharedPreferences_Intro(this);
        if (!preferences_intro.isFirst())
        {
            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            setContentView(R.layout.activity_intro);
            FindView();
            setIntro();
            setUpIndicator();
            setCurrent(0);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrent(position);
                }
            });
            Listener();
        }

    }

    private void FindView()
    {
        viewPager2 = findViewById(R.id.viewpager_intro);
        lnrIndicator = findViewById(R.id.lnr_idicator);
        imgForward = findViewById(R.id.img_forward);
        imgPrev = findViewById(R.id.img_prev);
        imgDone = findViewById(R.id.img_done);
    }

    private void Listener()
    {
        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() + 1 < intro_adapter.getItemCount())
                {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
                else
                {

//                    Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
//                    startActivity(intent);
                }
            }
        });

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences_intro.saveIntroInfo(false);
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() + 1 <= intro_adapter.getItemCount())
                {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                }
            }
        });
    }

    private void setIntro()
    {
        intro_models.add(new Intro_Model(R.drawable.ic_learning_bro,"دوره های تخصصی",getString(R.string.loremipsum)));
        intro_models.add(new Intro_Model(R.drawable.ic_mathematics_bro,"خرید اشتراک",getString(R.string.loremipsum)));
        intro_models.add(new Intro_Model(R.drawable.ic_mathematics_pana,"ویدیو های انگیزشی",getString(R.string.loremipsum)));
        intro_adapter = new Intro_Adapter(intro_models,getApplicationContext());
        viewPager2.setAdapter(intro_adapter);
    }

    private void setUpIndicator ()
    {
        ImageView[] indicator = new ImageView[intro_adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0 ; i < indicator.length ; i++)
        {
            indicator[i] = new ImageView(this);
            indicator[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.shape_indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            lnrIndicator.addView(indicator[i]);
        }
    }

    private void setCurrent(int index)
    {
        int child = lnrIndicator.getChildCount();
        for (int i = 0 ; i <child ; i++)
        {
            ImageView imageView = (ImageView) lnrIndicator.getChildAt(i);
            if (i == index)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.shape_indicator_active));
            }
            else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.shape_indicator_inactive));
            }
        }
        if (index == intro_adapter.getItemCount() - 1)
        {
//            Toast.makeText(this, "اتمام" , Toast.LENGTH_SHORT).show();
            imgForward.setVisibility(View.GONE);
            imgDone.setVisibility(View.VISIBLE);
        }
        else if (index == 1)
        {
            imgPrev.setVisibility(View.VISIBLE);
            imgDone.setVisibility(View.INVISIBLE);
            imgForward.setVisibility(View.VISIBLE);
        }
        else
        {
            imgForward.setVisibility(View.VISIBLE);
            imgPrev.setVisibility(View.INVISIBLE);
            imgDone.setVisibility(View.INVISIBLE);
        }
    }

}