package com.example.hrvmobileapp;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class HomePage extends AppCompatActivity implements DialogFragment.OnInputListener {


    public TextView mInputDisplay, tvinput;
    public String mInput;



    private HomePageBinding binding_home;


    FirebaseDatabase firebaseDatabase;

    PointsGraphSeries<DataPoint> series;
    Dialog dialog;
    GraphView graphView;
    DateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    Map<String, Double> htSeries = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_home = HomePageBinding.inflate(getLayoutInflater());
        View view = binding_home.getRoot();
        setContentView(view);
        //   value_string = new ArrayList<>();
        //   value_double= new ArrayList<>();

        mInputDisplay = findViewById(R.id.input_display);
        mInputDisplay.setTextSize(12);

        tvinput = findViewById(R.id.input);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //graph
        graphView = binding_home.graphId;
        series = new PointsGraphSeries<>();


        //popup
        dialog = new Dialog(this);

        showHrvData();
        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
        graphView.getGridLabelRenderer().setNumVerticalLabels(5);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setYAxisBoundsManual(true); // Prevents auto-rescaling the Y-axis
        graphView.getViewport().setXAxisBoundsManual(true);
        Viewport viewport = graphView.getViewport();
        //viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMinX(0);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        viewport.setMaxY(2);
        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setSize(8);
        graphView.getViewport().setScalableY(true);
        series.setColor(Color.rgb(117, 53, 173));
        graphView.getGridLabelRenderer().setTextSize(12f);
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);


        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(value);
                }
                return super.formatLabel(value, isValueX);
            }
        });


        binding_home.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });
    }//oncreate

    private void showHrvData() {
        DatabaseReference reference = firebaseDatabase.getReference("hrvData")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    graphView.addSeries(series);

                    for (DataSnapshot i : snapshot.getChildren()) {
                        HrvData value = i.getValue(HrvData.class);
                        if (value != null) {
                            if (!htSeries.containsKey("01.01.2000 " + value.time)) {
                                htSeries.put("01.01.2000 " + value.time, value.hrv);
                            }
                        }
                    }

                    for (String key : new TreeMap<>(htSeries).keySet()) {
                        try {
                            Date dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS")
                                    .parse(key);
                            double y = htSeries.get(key);
                            series.appendData(new DataPoint(dt, y), true, 130000);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkAlerts2();
                        }
                    }, 8000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkAlerts();
                        }
                    }, 30000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }//showuserdata

    public void checkAlerts() {
        for (String key : new TreeMap<>(htSeries).keySet()) {
            double y = htSeries.get(key);
            double thres1 = 1.81;
            if (thres1 < y) {
                tvinput.setVisibility(View.VISIBLE);
                DialogFragment dialog = new DialogFragment();
                dialog.show(getFragmentManager(), "MyCustomDialog");
                break;

            }
        }


    }//check

    public void checkAlerts2() {
        for (String key : new TreeMap<>(htSeries).keySet()) {
            double y = htSeries.get(key);
            double thres2 = 0.8;
            if (thres2 > y) {
                dialog.setContentView(R.layout.dialogfragmentdown);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        }


    }//check

    private void setInputToTextView() {
        mInputDisplay.setText(mInput);
    }

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);
        mInput = input;
        setInputToTextView();
    }

}
