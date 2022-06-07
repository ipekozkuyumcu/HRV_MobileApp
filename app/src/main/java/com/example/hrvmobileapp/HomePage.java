package com.example.hrvmobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.HomePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomePage extends AppCompatActivity {
    public String mail,resting, cold;
    private HomePageBinding binding_home;


    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    //  FirebaseUser firebaseUser;
    PointsGraphSeries<DataPoint> series;
    GraphView graphView;
   // ArrayList<String> value_string;
   // ArrayList<Double> value_double;
    private DateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_home = HomePageBinding.inflate(getLayoutInflater());
        View view = binding_home.getRoot();
        setContentView(view);



        graphView = binding_home.graphId;
        series = new PointsGraphSeries<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

     //   value_string = new ArrayList<>();
     //   value_double= new ArrayList<>();
        for (int i = 0; i < 12999; i++) {
            reference = firebaseDatabase.getReference("hrvData")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(String.valueOf(i));

            showHrvData();


        }





        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
        graphView.getGridLabelRenderer().setNumVerticalLabels(5);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setYAxisBoundsManual(true); // Prevents auto-rescaling the Y-axis
        graphView.getViewport().setXAxisBoundsManual(true);
        Viewport viewport = graphView.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMinX(0);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        series.setShape(PointsGraphSeries.Shape.RECTANGLE);
        graphView.getViewport().setScalableY(true);
        series.setColor(Color.rgb(117,53,173));
        graphView.getGridLabelRenderer().setTextSize(12f);







        binding_home.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });
    }





    private void showHrvData(){
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HrvData value = snapshot.getValue(HrvData.class);


                double y;
               // double x = Double.parseDouble(value.time);
                y = value.hrv;
                long x = new Date().getTime();
                series.appendData(new DataPoint(x,y), true, 13000);


                graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if(isValueX){
                            return sdf.format(x);
                        }
                        return super.formatLabel(value, isValueX);
                    }
                });


            }//ondatachange


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//showuserdata





}