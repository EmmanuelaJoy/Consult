 package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
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

public class UserDetails extends AppCompatActivity {

    TextInputEditText mFullName, mEmail, mPassword, mPhone, mProfession;
    ImageView mProfile;
    Button update;
    String user_email, user_fullnames, user_phone, user_username, user_password;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        reference = FirebaseDatabase.getInstance().getReference("users");
        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mPassword = findViewById(R.id.password);
        mProfession = findViewById(R.id.profession);
        mProfile = findViewById(R.id.profileImageUD);

        update = findViewById(R.id.update);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.keepSynced(true);

        showAllUserData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });


    }

    private void showAllUserData() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users = snapshot.getValue(Users.class);

                mFullName.setText(users.getUsername());
                mEmail.setText(users.getEmail());
                mPassword.setText(users.getPassword());
                mPhone.setText(users.getPhone());
                mProfession.setText(users.getProfession());

                if (users.getImageURL().equals("default")) {
                    mProfile.setImageResource(R.drawable.ic_account);
                } else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(mProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserData(){
        if(isFullNameChanged() || isEmailChanged() || isPhoneNumberChanged() || isPasswordChanged()){
            Toast.makeText(this, "Your data has been updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPasswordChanged() {
        String value = mPassword.getEditableText().toString();
        if(!user_password.equals(mPassword.getText().toString())){
            if (value.isEmpty()) {
                mPassword.setError("Field cannot be empty");
                return false;
            }else {
                reference.child(user_username).child("password").setValue(mPassword.getText().toString());
                return true;
            }
        }else{
            return false;
        }
    }

    private boolean isPhoneNumberChanged() {
        if(!user_phone.equals(mPhone.getText().toString())){
            if (mPhone.getEditableText().toString().isEmpty()) {
                mPhone.setError("Field cannot be empty");
                return false;
            }else {
                reference.child(user_username).child("phone").setValue(mFullName.getText().toString());
                return true;
            }
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        String value = mEmail.getEditableText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(!user_email.equals(mEmail.getText().toString())){
            if (value.isEmpty()) {
                mEmail.setError("Field cannot be empty");
                return false;
            }else if(!value.matches(emailPattern)){
                mEmail.setError("Invalid email address");
                return false;
            } else {
                reference.child(user_username).child("email").setValue(mEmail.getText().toString());
                return true;
            }
        }else{
            return false;
        }
    }

    private boolean isFullNameChanged() {
        if(!user_fullnames.equals(mFullName.getText().toString())){
            if (mFullName.getEditableText().toString().isEmpty()) {
                mFullName.setError("Field cannot be empty");
                return false;
            }else {
                reference.child(user_username).child("fullnames").setValue(mFullName.getText().toString());
                return true;
            }
        }else{
            return false;
        }
    }
}