package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.moringaschool.consult.R;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String user_email, user_username, user_password, user_fullnames;
    String names, email, password, username;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        title = findViewById(R.id.title);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

//        //user-details
//        Intent intent = getIntent();
//        user_email = intent.getStringExtra("email");
//        user_username = intent.getStringExtra("username");
//        user_password = intent.getStringExtra("password");
//        user_fullnames = intent.getStringExtra("fullnames");
//
//        title.setText("Welcome " + user_fullnames);
//        names = user_fullnames;
//        password = user_password;
//        email = user_email;
//        username = user_username;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeM:
                        return true;
                    case R.id.tasksM:
                        startActivity(new Intent(getApplicationContext(), TasksActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profileM:
//                        profileIntent.putExtra("email", email);
//                        profileIntent.putExtra("username", username);
//                        profileIntent.putExtra("fullnames", names);
//                        profileIntent.putExtra("password", password);
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.chatButton:h:
                        startActivity(new Intent(getApplicationContext(), TextActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pageM:
                        startActivity(new Intent(getApplicationContext(), PagerActivty.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    public void message(View view){
        startActivity(new Intent(getApplicationContext(), TextActivity.class));
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId()== R.id.homeM){
            super.onBackPressed();
            finish();
        }

    }
}