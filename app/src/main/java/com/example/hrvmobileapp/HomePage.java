package com.example.hrvmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.HomePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomePage extends AppCompatActivity {

    private FirebaseAuth dataAuth;
    public String mail,resting, cold;
    private HomePageBinding binding_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_home = HomePageBinding.inflate(getLayoutInflater());
        View view = binding_home.getRoot();
        setContentView(view);

        dataAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =dataAuth.getCurrentUser();


        if(firebaseUser == null){
            Toast.makeText(this, "There is no profile info", Toast.LENGTH_SHORT).show();
        }
        else{
            showHrvData(firebaseUser);
        }

        binding_home.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });

    }//onCreate

    private void showHrvData(FirebaseUser firebaseUser){
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hrvData");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HrvData hrvDetail = snapshot.getValue(HrvData.class);
                if(hrvDetail != null){
                    resting= hrvDetail.resting;
                    cold = hrvDetail.cold;

                    binding_home.fbResting.setText(resting);
                    binding_home.fbCold.setText(cold);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }//showuserdata


}