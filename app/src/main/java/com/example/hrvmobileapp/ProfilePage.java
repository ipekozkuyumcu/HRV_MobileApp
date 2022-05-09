package com.example.hrvmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.hrvmobileapp.databinding.ProfilePageBinding;

public class ProfilePage extends AppCompatActivity {


    private ProfilePageBinding binding_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_profile = ProfilePageBinding.inflate(getLayoutInflater());
        View view = binding_profile.getRoot();
        setContentView(view);


        binding_profile.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

    }
}
