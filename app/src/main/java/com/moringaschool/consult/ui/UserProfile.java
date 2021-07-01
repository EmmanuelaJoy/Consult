package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    TextView name,email;
    ImageView profile;
    String user_email, user_fullnames, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        profile.findViewById(R.id.profileImage);
//        name = findViewById(R.id.displayName);
//        email = findViewById(R.id.displayUsername);
//
//        mAuth = FirebaseAuth.getInstance();



    }

//        private void showAllUserData() {
//            Intent profileIntent = getIntent();
//            user_email = profileIntent.getStringExtra("email");
//            user_fullnames = profileIntent.getStringExtra("fullnames");
//            user_id = firebaseUser.getUid();
//
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//            Query checkUser = reference.orderByChild(user_id);
//            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                    if(snapshot.exists()){
//                        String emailFromDB= snapshot.child(user_id).child("status").getValue(String.class);
//                        String fullNamesFromDB= snapshot.child(user_id).child("username").getValue(String.class);
//                        email.setText(emailFromDB);
//                        name.setText(fullNamesFromDB);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                }
//            });
//        }

    public void user(View view){
        Intent intent = new Intent(UserProfile.this, UserDetails.class);
        startActivity(intent);
    }
}