package com.example.messageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView logsignup;
    Button btnlog;
    EditText email, password;
    FirebaseAuth auth;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen bekleyiniz.");
        progressDialog.setCancelable(false);


        auth = FirebaseAuth.getInstance();
        btnlog = findViewById(R.id.btnlog);
        email = findViewById(R.id.editTextLogMail);
        password= findViewById(R.id.editTextLogPassword);
        logsignup=findViewById(R.id.logsignup);

        logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login.this,registration.class);
                startActivity(intent);
                finish();
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email =email.getText().toString();
                String Pass = password.getText().toString();

                if (TextUtils.isEmpty(Email)){
                    progressDialog.dismiss();
                    Toast.makeText(login.this,"Email Giriniz",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Pass)){
                    progressDialog.dismiss();
                    Toast.makeText(login.this,"Şifre Giriniz",Toast.LENGTH_SHORT).show();
                }else if(!Email.matches(emailPattern)){
                    progressDialog.dismiss();
                    email.setError("Yanlış Email!");
                }else if(password.length()<6){
                    progressDialog.dismiss();
                    password.setError("Şifre 6 karakterden fazla olmalı");
                    Toast.makeText(login.this,"Şifre 6 karakterden uzun olmalı",Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressDialog.show();
                            try {
                                Intent intent= new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }catch (Exception e){
                                Toast.makeText(login.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                }

            }
        });



    }
}