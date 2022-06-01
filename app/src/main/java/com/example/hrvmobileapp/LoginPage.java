package com.example.hrvmobileapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.LoginPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private LoginPageBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
         mAuth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);


      /*  binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        }); */

    }//onCreate


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                logIn();

                break;
            case R.id.btn_signup:
                Intent intent = new Intent(this,SignupPage.class);
                startActivity(intent);
                break;
        }

    }

    private void logIn(){
        String mail = binding.etMail.getText().toString().trim();
        String password =binding.etPassword.getText().toString().trim();


        if(mail.isEmpty()){
            binding.etMail.setError("Mail is required");
            binding.etMail.requestFocus();
            return;

        }
        if(password.isEmpty()){
            binding.etPassword.setError("Password is required");
            binding.etPassword.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            binding.etMail.setError("Provide valid mail");
            binding.etMail.requestFocus();
        }
        if(password.length() < 8){
            binding.etPassword.setError(" Password should be min 8 char");
        }

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginPage.this,ProfilePage.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(LoginPage.this, "Check your login info", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }//login

}