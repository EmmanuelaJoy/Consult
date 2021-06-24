package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.consult.R;
import com.moringaschool.consult.adapter.SlideViewPagerAdapter;

public class SplashScreen extends AppCompatActivity {


    TextView appName, info;
    ImageView background;
    public static ViewPager viewPager;
    SlideViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appName = findViewById(R.id.appName);
        info = findViewById(R.id.moreInfo);
        background = findViewById(R.id.backgroundImg);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new SlideViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        background.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
        appName.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
        info.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
    }

}