package com.example.hrvmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.hrvmobileapp.databinding.ProfilePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {

    private FirebaseAuth profileAuth;

    private ProfilePageBinding binding_profile;
    private String mail, password, name, surname, height, weight, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_profile = ProfilePageBinding.inflate(getLayoutInflater());
        View view = binding_profile.getRoot();
        setContentView(view);

        profileAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = profileAuth.getCurrentUser();
        
        if(firebaseUser == null){
            Toast.makeText(this, "There is no profile info", Toast.LENGTH_SHORT).show();
        }
        else{
            showProfileData(firebaseUser);
        }


        binding_profile.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

    }//onCreate

    private void showProfileData(FirebaseUser firebaseUser){
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userDetail = snapshot.getValue(User.class);
                if(userDetail != null){
                    mail = firebaseUser.getEmail();
                    name = userDetail.name;
                    password = userDetail.password;
                    surname = userDetail.surname;
                    weight = userDetail.weight;
                    height = userDetail.height;
                    age = userDetail.age;

                    binding_profile.fbName.setText(name);
                    binding_profile.fbSurname.setText(surname);
                    binding_profile.fbMail.setText(mail);
                    binding_profile.fbPassword.setText(password);
                    binding_profile.fbWeight.setText(weight);
                    binding_profile.fbHeight.setText(height);
                    binding_profile.fbAge.setText(age);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }//showuserdata



}
