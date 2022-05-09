package com.example.hrvmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.SignupPageBinding;

public class SignupPage extends AppCompatActivity {

    private SignupPageBinding binding_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_sign = SignupPageBinding.inflate(getLayoutInflater());
        View view = binding_sign.getRoot();
        setContentView(view);

        binding_sign.signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
            }
        });

        binding_sign.logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });

    }
}