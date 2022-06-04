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
    DatabaseReference reference;
    FirebaseUser fu;
    private ProfilePageBinding binding_profile;
    private String mail, password, name, surname, height, weight, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_profile = ProfilePageBinding.inflate(getLayoutInflater());
        View view = binding_profile.getRoot();
        setContentView(view);

        reference = FirebaseDatabase.getInstance().getReference("users");
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
        binding_profile.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(view);

            }
        });


    }//onCreate

    private void showProfileData(FirebaseUser firebaseUser){
        String userId = firebaseUser.getUid();

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

    private void updateData(View view){
        if(isMailChanged()|| isPasswordChanged()||isNameChanged()||isSurnameChanged()
        ||isWeightChanged()|| isHeightChanged()||isAgeChanged()){
            Toast.makeText(this, "Data has been updated",Toast.LENGTH_LONG).show();

        }
    }

    private boolean isMailChanged(){

        if(!mail.equals(binding_profile.fbMail.getText().toString())){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mail").setValue(binding_profile.fbMail.getText().toString());
            return true;


        }else{
            return false;
        }

    }

    private boolean isPasswordChanged(){
        if(!password.equals(binding_profile.fbPassword.getEditableText().toString())){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("password").setValue(binding_profile.fbPassword.getEditableText().toString());
            return true;


        }else{
            return false;
        }

    }

    private boolean isNameChanged(){

        if(!name.equals(binding_profile.fbName.getText().toString())){
            reference .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(binding_profile.fbName.getText().toString());
            return true;


        }else{
            return false;
        }

    }
    private boolean isSurnameChanged(){
        if(!surname.equals(binding_profile.fbSurname.getEditableText().toString())){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("surname").setValue(binding_profile.fbSurname.getEditableText().toString());
            return true;


        }else{
            return false;
        }

    }

    private boolean isWeightChanged(){
        if(!weight.equals(binding_profile.fbWeight.getEditableText().toString())){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("weight").setValue(binding_profile.fbWeight.getEditableText().toString());
            return true;


        }else{
            return false;
        }

    }

    private boolean isHeightChanged(){
        if(!height.equals(binding_profile.fbHeight.getEditableText().toString())){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("height").setValue(binding_profile.fbHeight.getEditableText().toString());
            return true;


        }else{
            return false;
        }

    }

    private boolean isAgeChanged(){
        if(!age.equals(binding_profile.fbAge.getEditableText().toString())){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("age").setValue(binding_profile.fbAge.getEditableText().toString());
            return true;


        }else{
            return false;
        }

    }



}
