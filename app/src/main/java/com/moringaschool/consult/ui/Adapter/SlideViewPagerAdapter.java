package com.moringaschool.consult.adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.RegisterActivity;
import com.moringaschool.consult.ui.SplashScreen;

public class SlideViewPagerAdapter extends PagerAdapter {

    Context context;

    public SlideViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_introduction_1, container, false);

        TextView skip = view.findViewById(R.id.skip);
        LottieAnimationView lottie = view.findViewById(R.id.lottie);
        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);
        MaterialButton button = view.findViewById(R.id.button);

        ImageView disc1 = view.findViewById(R.id.disc1);
        ImageView disc2 = view.findViewById(R.id.disc2);
        ImageView disc3 = view.findViewById(R.id.disc3);
        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashScreen.viewPager.setCurrentItem(position-1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashScreen.viewPager.setCurrentItem(position+1);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        switch (position){
            case 0:
                skip.setText("Skip Intro");
                back.setVisibility(View.INVISIBLE);
                lottie.setAnimation(R.raw.hworkers);
                title.setText("Welcome to Consult");
                description.setText("A clinical communications and collaboration platform that coordinates activities and data flow between health workers.");
                button.setText("Next");
                disc1.setImageResource(R.drawable.selected);
                disc2.setImageResource(R.drawable.unselected);
                disc3.setImageResource(R.drawable.unselected);
                break;
            case 1:
                lottie.setAnimation(R.raw.healthsymbols);
                title.setText("Key Features");
                description.setText("Consult has four key product areas that are underpinned by our extensive data and security credentials.");
                button.setText("Next");
                disc1.setImageResource(R.drawable.unselected);
                disc2.setImageResource(R.drawable.selected);
                disc3.setImageResource(R.drawable.unselected);
                break;
            case 2:
                lottie.setAnimation(R.raw.onlinedoctor);
                title.setText("Clinical Tasks and Priorities");
                description.setText("Create clarity around competing priorities and make it easy for colleagues to pick up and share feedback on tasks.");
                disc1.setImageResource(R.drawable.unselected);
                disc2.setImageResource(R.drawable.unselected);
                disc3.setImageResource(R.drawable.selected);
                button.setText("Get Started");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, RegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}