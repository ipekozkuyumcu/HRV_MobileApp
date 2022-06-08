package com.example.hrvmobileapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrvmobileapp.databinding.SignupPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SignupPage extends AppCompatActivity   {

    private SignupPageBinding binding_sign;
    private FirebaseAuth mAuth;
  //  FloatingActionButton  fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding_sign = SignupPageBinding.inflate(getLayoutInflater());
        View view = binding_sign.getRoot();
        setContentView(view);


        mAuth = FirebaseAuth.getInstance();

        binding_sign.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), onDateSetListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });

        binding_sign.signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });

    }//onCreate
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
                binding_sign.etAge.setText(Integer.toString(calculateAge(c.getTimeInMillis())));

        }
    };
    int calculateAge (long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;

    }



    private void signUp(){

        String mail = binding_sign.etMail.getText().toString().trim();
        String password =binding_sign.etPassword.getText().toString().trim();
        String name =binding_sign.etName.getText().toString().trim();
        String surname =binding_sign.etSurname.getText().toString().trim();
        String height =binding_sign.etHeight.getText().toString().trim();
        String weight =binding_sign.etWeight.getText().toString().trim();
        String age =binding_sign.etAge.getText().toString().trim();

    //   String resting = binding_sign.etAge.getText().toString().trim();
    //   String cold = binding_sign.etAge.getText().toString().trim();

        if(mail.isEmpty()){
            binding_sign.etMail.setError("Mail is required");
            binding_sign.etMail.requestFocus();
            return;

        }
        if(password.isEmpty()){
            binding_sign.etPassword.setError("Password is required");
            binding_sign.etPassword.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            binding_sign.etMail.setError("Provide valid mail");
            binding_sign.etMail.requestFocus();
        }
        if(password.length() < 8){
            binding_sign.etPassword.setError(" Password should be min 8 char");
        }
        if(name.isEmpty()){
            binding_sign.etName.setError("Name is required");
            binding_sign.etName.requestFocus();
            return;

        }
        if(surname.isEmpty()){
            binding_sign.etSurname.setError("Surname is required");
            binding_sign.etSurname.requestFocus();
            return;

        }
        if(height.isEmpty()){
            binding_sign.etHeight.setError("Height is required");
            binding_sign.etHeight.requestFocus();
            return;

        }
        if(weight.isEmpty()){
            binding_sign.etWeight.setError("Weight is required");
            binding_sign.etWeight.requestFocus();
            return;

        }
        if(age.isEmpty()){
            binding_sign.etAge.setError("Age is required");
            binding_sign.etAge.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(mail,password,name,surname,height,weight,age);


                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupPage.this, "Has been registered", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(SignupPage.this, "Try again", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignupPage.this, "FAIL", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }//signup



}