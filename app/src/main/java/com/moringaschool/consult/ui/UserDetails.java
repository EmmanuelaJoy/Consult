 package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.consult.R;

import org.jetbrains.annotations.NotNull;

public class UserDetails extends AppCompatActivity {

    TextInputEditText mFullName, mEmail, mPassword, mPhone;
    Button update;
    String user_email, user_fullnames, user_phone, user_username, user_password;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        reference = FirebaseDatabase.getInstance().getReference("users");
        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mPassword = findViewById(R.id.password);
        update = findViewById(R.id.update);


        showAllUserData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });


    }

    private void showAllUserData() {
        Intent profileIntent = getIntent();
        user_email = profileIntent.getStringExtra("email");
        user_fullnames = profileIntent.getStringExtra("fullnames");
        user_phone = profileIntent.getStringExtra("phone");
        user_password = profileIntent.getStringExtra("password");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("username").equalTo(user_username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordFromDB= snapshot.child(user_username).child("password").getValue(String.class);
                    String phoneFromDB = snapshot.child(user_username).child("phone").getValue(String.class);
                    String emailFromDB= snapshot.child(user_username).child("email").getValue(String.class);
                    String fullNamesFromDB= snapshot.child(user_username).child("fullnames").getValue(String.class);
                    mPassword.setText(passwordFromDB);
                    mPhone.setText(phoneFromDB);
                    mEmail.setText(emailFromDB);
                    mFullName.setText(fullNamesFromDB);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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