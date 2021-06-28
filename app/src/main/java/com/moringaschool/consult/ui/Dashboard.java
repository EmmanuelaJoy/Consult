package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.consult.R;

public class Dashboard extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        mAuth = FirebaseAuth.getInstance();
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
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.chatButton:
                        startActivity(new Intent(getApplicationContext(), TextActivity.class));
                        overridePendingTransition(0,0);
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