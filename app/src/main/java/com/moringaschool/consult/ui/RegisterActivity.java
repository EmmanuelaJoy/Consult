
package com.moringaschool.consult.ui;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.consult.R;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rey.material.drawable.ThemeDrawable;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputEditText mFullName, mEmail, mPassword, mPhone, mProfession;

    Button mRegisterBtn;
    TextView mCreateTxt;
    TextView mLoginBtn;
    LinearLayout passwordValidation;
    MaterialCardView card1, card2, card3, card4, card5;
    TextView card1Text, card2Text, card3Text, card4Text, card5Text;
    private boolean is6char = false, hasUpper = false, hasnum = false, hasSpecialSymbol = false, hasLower = false;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);

        mFullName = findViewById(R.id.reg_fullname);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mProfession = findViewById(R.id.reg_profession);
        mPhone = findViewById(R.id.reg_phone);
        mRegisterBtn = findViewById(R.id.reg_btn);
        mLoginBtn = findViewById(R.id.loginbtn);
        mCreateTxt = findViewById(R.id.createText);

        passwordValidation = findViewById(R.id.passwordValidation);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);

        card1Text = findViewById(R.id.card1text);
        card2Text = findViewById(R.id.card2text);
        card3Text = findViewById(R.id.card3text);
        card4Text = findViewById(R.id.card4text);
        card5Text = findViewById(R.id.card5text);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            finish();
//        }
        mCreateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        inputChanged();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullNames() || !validateEmail() || !validateProfession() || !validatePhoneNumber() || !validatePassword()) {
                    return;
                } else {
                    final String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    final String fullName = mFullName.getText().toString();
                    final String profession = mProfession.getText().toString();
                    final String phone = mPhone.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);

                    // register the user in firebase

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = fAuth.getCurrentUser();
                                // send verification link
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                                if (user != null) {

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("username", fullName);
                                    hashMap.put("email", email);
                                    hashMap.put("profession", profession);
                                    hashMap.put("phone", phone);
                                    hashMap.put("password", password);
                                    hashMap.put("id", user.getUid());
                                    hashMap.put("imageURL", "default");
                                    hashMap.put("status", "offline");


                                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            if (task.isSuccessful()) {

                                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(RegisterActivity.this,
                                                        LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


                                            }
                                        }
                                    });


                                }

                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

    }

    private Boolean validateFullNames() {
        String value = mFullName.getEditableText().toString();
        if (value.isEmpty()){
            mFullName.setError("Field cannot be empty");
            return false;
        }else{
            mFullName.setError(null);
            return true;
        }
    }

    private Boolean validateProfession() {
        String value = mProfession.getEditableText().toString();
        if (value.isEmpty()){
            mProfession.setError("Field cannot be empty");
            return false;
        }else{
            mProfession.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String value = mPhone.getEditableText().toString();
        if (value.isEmpty()){
            mPhone.setError("Field cannot be empty");
            return false;
        }else{
            mPhone.setError(null);
            return true;
        }
    }


    private Boolean validateEmail(){
        String value = mEmail.getEditableText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (value.isEmpty()){
            mEmail.setError("Field cannot be empty");
            return false;
        } else if(!value.matches(emailPattern)){
            mEmail.setError("Invalid email address");
            return false;
        }else{
            mEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        if (!is6char || !hasUpper || !hasnum || !hasSpecialSymbol ||!hasLower){
            mPassword.setError("Password is too weak");
            return false;
        }else{
            mPassword.setError(null);
            return true;
        }
    }

    private  void inputChanged() {
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("ResourceType")
    private void passwordValidation(){
        passwordValidation.setVisibility(View.VISIBLE);
        String value = mPassword.getEditableText().toString();

        // 6 character
        if(value.length()>= 6)
        {
            is6char = true;
            card1.setCardBackgroundColor(Color.parseColor(getString(R.color.colorValid)));
            card1Text.setTextColor(Color.parseColor(getString(R.color.colorPrimaryText)));
        }else{
            is6char = false;
            card1.setCardBackgroundColor(Color.parseColor(getString(R.color.colorButtons)));
            card1Text.setTextColor(Color.GRAY);
        }

        //upper case
        if(value.matches("(.*[A-Z].*)"))
        {
            hasUpper = true;
            card2.setCardBackgroundColor(Color.parseColor(getString(R.color.colorValid)));
            card2Text.setTextColor(Color.parseColor(getString(R.color.colorPrimaryText)));
        }else{
            hasUpper = false;
            card2.setCardBackgroundColor(Color.parseColor(getString(R.color.colorButtons)));
            card2Text.setTextColor(Color.GRAY);
        }

        //lower case
        if(value.matches("(.*[a-z].*)"))
        {
            hasLower = true;
            card3.setCardBackgroundColor(Color.parseColor(getString(R.color.colorValid)));
            card3Text.setTextColor(Color.parseColor(getString(R.color.colorPrimaryText)));
        }else{
            hasLower = false;
            card3.setCardBackgroundColor(Color.parseColor(getString(R.color.colorButtons)));
            card3Text.setTextColor(Color.GRAY);
        }

        //number
        if(value.matches("(.*[0-9].*)"))
        {
            hasnum = true;
            card4.setCardBackgroundColor(Color.parseColor(getString(R.color.colorValid)));
            card4Text.setTextColor(Color.parseColor(getString(R.color.colorPrimaryText)));
        }else{
            hasUpper = false;
            card4.setCardBackgroundColor(Color.parseColor(getString(R.color.colorButtons)));
            card4Text.setTextColor(Color.GRAY);
        }

        //symbol
        if(value.matches("^(?=.*[_.()$&@]).*$")){
            hasSpecialSymbol = true;
            card5.setCardBackgroundColor(Color.parseColor(getString(R.color.colorValid)));
            card5Text.setTextColor(Color.parseColor(getString(R.color.colorPrimaryText)));
        }else{
            hasSpecialSymbol = false;
            card5.setCardBackgroundColor(Color.parseColor(getString(R.color.colorButtons)));
            card5Text.setTextColor(Color.GRAY);
        }
    }
}

//                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
//                            userID = fAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
//                            Map<String,Object> user = new HashMap<>();
//                            user.put("fName",fullName);
//                            user.put("email",email);
//                            user.put("phone",phone);
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: " + e.toString());
//                                }
//                            });
//                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//
//                        }else {
//                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//            }
//        });
//
//







