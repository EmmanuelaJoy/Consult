package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.consult.R;

public class SplashScreen extends AppCompatActivity {


    TextView appName, info;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appName = findViewById(R.id.appName);
        info = findViewById(R.id.moreInfo);
        background = findViewById(R.id.backgroundImg);



//        background.animate().translationY(-1800).setDuration(1000).setStartDelay(4000);
//        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
//        info.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
    }
}