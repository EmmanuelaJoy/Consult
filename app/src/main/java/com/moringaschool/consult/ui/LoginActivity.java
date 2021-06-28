package com.moringaschool.consult.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import com.moringaschool.consult.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.rey.material.widget.CheckBox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mEmail,mPassword;
    TextInputEditText mPhone;
    MaterialButton mLoginBtn;

    TextView mCreateBtn,forgotTextLink,AdminLink,NotAdminLink;
    CheckBox chkBoxRememberMe;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

//        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
//        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mPhone = findViewById(R.id.reg_phone);

        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginbtn);
        mCreateBtn = findViewById(R.id.logintxt);
        forgotTextLink = findViewById(R.id.forgot_password);
        chkBoxRememberMe = findViewById(R.id.remember_me_chkb);


        Paper.init(this);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                });

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });


//         AdminLink.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view)
//             {
//                 mLoginBtn.setText("Login Admin");
//                 AdminLink.setVisibility(View.INVISIBLE);
//                 NotAdminLink.setVisibility(View.VISIBLE);
//                 parentDbName = "Admins";
//             }
//         });
//         NotAdminLink.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view)
//             {
//                 mLoginBtn.setText("Login");
//                 AdminLink.setVisibility(View.VISIBLE);
//                 NotAdminLink.setVisibility(View.INVISIBLE);
//                 parentDbName = "Users";
//             }
//         });
//     }


//     private void AllowAccessToAccount(final String phone, final String password)
//     {
//         if(chkBoxRememberMe.isChecked())
//         {
//             Paper.book().write(Prevalent.UserPhoneKey, phone);
//             Paper.book().write(Prevalent.UserPasswordKey, password);
//         }

//         final DatabaseReference RootRef;
//         RootRef = FirebaseDatabase.getInstance().getReference();


//         RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//             {
//                 if (dataSnapshot.child(parentDbName).child(phone).exists())
//                 {
//                     Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

//                     if (usersData.getPhone().equals(phone))
//                     {
//                         if (usersData.getPassword().equals(password))
//                         {
//                             if (parentDbName.equals("Admins"))
//                             {
//                                 Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
//                                 progressBar.setVisibility(View.GONE);

//                                 Intent intent = new Intent(LoginActivity.this, TaskActivity.class);
//                                 startActivity(intent);
//                             }
//                             else if (parentDbName.equals("Users"))
//                             {
//                                 Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
//                                 progressBar.setVisibility(View.GONE);

//                                 Intent intent = new Intent(LoginActivity.this, TasksActivity.class);
//                                 Prevalent.currentOnlineUser = usersData;
//                                 startActivity(intent);
//                             }
//                         }
//                         else
//                         {
//                             progressBar.setVisibility(View.GONE);
//                             Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
//                         }
//                     }
//                 }
//                 else
//                 {
//                     Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
//                     progressBar.setVisibility(View.GONE);
//                 }
//             }

//             @Override
//             public void onCancelled(@NonNull DatabaseError databaseError) {

//             }
//         });    }


    }
}