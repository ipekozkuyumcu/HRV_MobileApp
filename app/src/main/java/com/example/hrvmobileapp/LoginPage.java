package com.example.hrvmobileapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.LoginPageBinding;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    private LoginPageBinding binding;
    private FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Auth
        mAuth = FirebaseAuth.getInstance();



        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginPage.this, "Lütfen giriş işlemi yapılırken bekleyiniz", Toast.LENGTH_LONG).show();
                logIn();

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Toast.makeText(LoginPage.this, "We have user", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(LoginPage.this, "There is no user", Toast.LENGTH_LONG).show();
                }

            }
        };

    }//onCreate

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

   /* @Override
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

    }*/

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

        Task<AuthResult> t = mAuth.signInWithEmailAndPassword(mail,password);

        t.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginPage.this,HomePage.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(LoginPage.this, "Check your login info", Toast.LENGTH_SHORT).show();
                }
            }
        });
        t.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        t.addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(LoginPage.this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        });


    }//login

}