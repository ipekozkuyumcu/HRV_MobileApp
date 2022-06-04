package com.example.hrvmobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.HomePageBinding;
import com.example.hrvmobileapp.databinding.LoginPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class HomePage extends AppCompatActivity {
    public String mail,resting, cold;
    private HomePageBinding binding_home;

    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
  //  FirebaseUser firebaseUser;
    PointsGraphSeries<DataPoint> series;
    GraphView graphView;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_home = HomePageBinding.inflate(getLayoutInflater());
        View view = binding_home.getRoot();
        setContentView(view);

        graphView = binding_home.graphId;
        series = new PointsGraphSeries<>();

        double x,y;
        x= -0.5;

        for (int i = 0; i <100; i++) {
            x = x + 0.15;
            y =Math.sin(x);
            series.appendData(new DataPoint(x,y),true,100);
        }
        graphView.addSeries(series);


        firebaseDatabase = FirebaseDatabase.getInstance();

    for (int i = 0; i<3; i++){
    reference =firebaseDatabase.getReference("hrvData")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .child(Integer.toString(i));

      showHrvData();
}






        binding_home.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
               // startActivity(intent);
            }
        });
    }





    private void showHrvData(){
      //  String userId = firebaseUser.getUid();
        /*reference = FirebaseDatabase.getInstance().getReference("hrvData");
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
        }); */



        reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HrvData value = snapshot.getValue(HrvData.class);

                        binding_home.fbResting.setText(value.resting);
                        binding_home.fbCold.setText(value.cold);






                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




    }//showuserdata





}