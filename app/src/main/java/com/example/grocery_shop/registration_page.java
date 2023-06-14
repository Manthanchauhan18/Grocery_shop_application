package com.example.grocery_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.grocery_shop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registration_page extends AppCompatActivity {
    Button btn_reg_register,btn_reg_cancle;
    EditText edit_reg_name , edit_reg_email , edit_reg_password;
    FirebaseAuth auth;
    String uid;
    DatabaseReference dbRef;
    ProgressBar progressBar_reg;
    int flag=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        btn_reg_register = findViewById(R.id.btn_reg_register);
        btn_reg_cancle = findViewById(R.id.btn_reg_cancle);

        edit_reg_name = findViewById(R.id.edit_reg_name);
        edit_reg_email = findViewById(R.id.edit_reg_email);
        edit_reg_password = findViewById(R.id.edit_reg_password);

        progressBar_reg = findViewById(R.id.progressbar_reg);
        progressBar_reg.setVisibility(View.GONE);

        btn_reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar_reg.setVisibility(View.VISIBLE);

                String name = edit_reg_name.getText().toString();
                String email = edit_reg_email.getText().toString();
                String password = edit_reg_password.getText().toString();


                String expname="^[A-Z a-z]{3,15}$";
                Pattern patname=Pattern.compile(expname);
                Matcher matname=patname.matcher(name);

                if(matname.matches()==false){
                    progressBar_reg.setVisibility(View.GONE);
                    Toast.makeText(registration_page.this, "Name is not valid or empty", Toast.LENGTH_SHORT).show();
                    edit_reg_name.requestFocus();
                    return;
                }

                String expemail ="^[a-zA-z0-9+_.-]+@[a-zA-Z0-9._]+$";
                Pattern patemail = Pattern.compile(expemail);
                Matcher matemail = patemail.matcher(email);

                if(matemail.matches()==false){
                    progressBar_reg.setVisibility(View.GONE);
                    Toast.makeText(registration_page.this, "Email id is not valid or empty", Toast.LENGTH_SHORT).show();
                    edit_reg_email.requestFocus();
                    return;
                }


                String exppass="^[A-Z]{1}[a-z0-9]{8,12}$";
                Pattern patpass=Pattern.compile(exppass);
                Matcher matpass=patpass.matcher(password);

                if(matpass.matches()==false ){
                    progressBar_reg.setVisibility(View.GONE);
                    Toast.makeText(registration_page.this, "Password is not valid or empty", Toast.LENGTH_SHORT).show();
                    edit_reg_password.requestFocus();
                    return;
                }


//                if(flag==0){
                    progressBar_reg.setVisibility(View.GONE);
                    auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        dbRef = FirebaseDatabase.getInstance().getReference("User");

                                        uid = dbRef.push().getKey();
                                        User user = new User(uid,name,email,password);
                                        dbRef.child(uid).setValue(user);


                                        Toast.makeText(registration_page.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent_register = new Intent(registration_page.this,MainActivity.class);
                                        startActivity(intent_register);
                                        finish();
                                    }else{
                                        Toast.makeText(registration_page.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
//            }
        });



        btn_reg_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(registration_page.this, "cancle pressed", Toast.LENGTH_SHORT).show();
                Intent intent_cancle = new Intent(registration_page.this , MainActivity.class);
                startActivity(intent_cancle);
            }
        });

    }
}