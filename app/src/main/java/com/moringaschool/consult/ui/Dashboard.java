package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.Adapter.MyNetwork;
import com.moringaschool.consult.ui.Model.Chatslist;
import com.moringaschool.consult.ui.Model.Users;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class Dashboard extends AppCompatActivity {

    List<Users> mUsersList;

    BottomNavigationView bottomNavigationView;
    TextView title;
    ImageView imageView;
    RecyclerView recyclerView;
    DatabaseReference reference;
    DatabaseReference reference1;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    MyNetwork myNetworkAdapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        title = findViewById(R.id.title);
        imageView = findViewById(R.id.profile);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        recyclerView = findViewById(R.id.myNetworkRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        reference1 = firebaseDatabase.getReference("Users");
        mUsersList = new ArrayList<>();
//        reference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                Users users = snapshot.getValue(Users.class);
//                mUsersList.add(users);
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
        myNetworkAdapter = new MyNetwork(mUsersList);
        recyclerView.setAdapter(myNetworkAdapter);


//        myNetworkAdapter = new MyNetwork(Users.class, R.layout.my_network, MyNetwork.NetworkViewHolder.class, reference1);
//        recyclerView.setAdapter(myNetworkAdapter);



//        layoutManager = new LinearLayoutManager(this);
//        recipesRecyclerView.setLayoutManager(layoutManager);
//        mRecipeListAdapter = new RecipeListAdapter(this, allRecipesClassList);
//        recipesRecyclerView.setAdapter(mRecipeListAdapter);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users = snapshot.getValue(Users.class);
                mUsersList.add(users);

                title.setText("Welcome\n" + users.getUsername());

                if (users.getImageURL().equals("default")) {

                    imageView.setImageResource(R.drawable.ic_account);
                } else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options();
            }
        });

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

    public void options(){
        String[] options = {"View Profile", "Log Out"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Choose an Option");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {


                if (i==0) {

                    profile();

                }

                if (i==1) {

                    logout();
                }
            }
        });

        builder.create().show();
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
        startActivity(intent);
    }

    public void profile(){
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
        overridePendingTransition(0,0);
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}