package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.Model.Users;

import org.jetbrains.annotations.NotNull;

public class UserProfile extends AppCompatActivity {

    DatabaseReference reference;
    TextView name,profession;
    ImageView profile;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.displayName);
        profession = findViewById(R.id.displayProfession);
        profile = findViewById(R.id.profileImage);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users = snapshot.getValue(Users.class);

                name.setText(users.getUsername());
                profession.setText(users.getPassword());

                if (users.getImageURL().equals("default")) {

                    profile.setImageResource(R.drawable.ic_account);
                } else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void user(View view){
        Intent intent = new Intent(UserProfile.this, UserDetails.class);
        startActivity(intent);
    }
}