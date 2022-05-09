package com.example.hrvmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.HomePageBinding;


public class HomePage extends AppCompatActivity {

    private HomePageBinding binding_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_home = HomePageBinding.inflate(getLayoutInflater());
        View view = binding_home.getRoot();
        setContentView(view);

        Intent intent;
        binding_home.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });

    }
}