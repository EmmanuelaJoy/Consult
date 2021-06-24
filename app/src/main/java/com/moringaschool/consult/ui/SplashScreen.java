package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.moringaschool.consult.R;
import com.moringaschool.consult.models.IntroductionFragment1;
import com.moringaschool.consult.models.IntroductionFragment2;
import com.moringaschool.consult.models.IntroductionFragment3;

public class SplashScreen extends AppCompatActivity {


    TextView appName, info;
    ImageView background;
    MaterialButton next1, next2, getStartedB;
    public static final int num_pages = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        appName = findViewById(R.id.appName);
        info = findViewById(R.id.moreInfo);
        background = findViewById(R.id.backgroundImg);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        background.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
        appName.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
        info.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    IntroductionFragment1 tab1 = new IntroductionFragment1();
                    return tab1;
                case 1:
                    IntroductionFragment2 tab2 = new IntroductionFragment2();
                    return tab2;
                case 2:
                    IntroductionFragment3 tab3 = new IntroductionFragment3();
                    return tab3;
            }
            return null;
        }

//        @Override
//        public Fragment createFragment(int position) {
//            switch (position){
//                case 0:
//                    IntroductionFragment1 tab1 = new IntroductionFragment1();
//                    return tab1;
//                case 1:
//                    IntroductionFragment2 tab2 = new IntroductionFragment2();
//                    return tab2;
//                case 2:
//                    IntroductionFragment3 tab3 = new IntroductionFragment3();
//                    return tab3;
//            }
//            return null;
//        }

//        @Override
//        public int getItemCount() {
//            return num_pages;
//        }

        @Override
        public int getCount() {
            return num_pages;
        }
    }
}