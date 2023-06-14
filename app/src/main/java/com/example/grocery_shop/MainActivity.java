package com.example.grocery_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery_shop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView txt_log_signUp;
    Button btn_log_login;
    EditText edit_log_email,edit_log_password;
    FirebaseAuth auth;
    ProgressBar progressBar;
    int flag=0;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;


    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_log_signUp = findViewById(R.id.txt_log_signUp);
        btn_log_login = findViewById(R.id.btn_log_login);
        edit_log_email = findViewById(R.id.edit_log_email);
        edit_log_password = findViewById(R.id.edit_log_password);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(MainActivity.this, main_home.class));
//            finish();
//        }

        // Check if the user is already logged in
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = loginPreferences.getBoolean("isLoggedIn", false);

        // If the user is already logged in, redirect to the home screen
        if (isLoggedIn) {
            Intent homeIntent = new Intent(MainActivity.this, main_home.class);
            startActivity(homeIntent);
            finish();
        }else{

            btn_log_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email = edit_log_email.getText().toString();
                    String password = edit_log_password.getText().toString();

                    if(email.equals("admin") && password.equals("admin")){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                        Intent intent_admin = new Intent(MainActivity.this , admin_home.class);
                        startActivity(intent_admin);
                    }else if(email.equals("") && password.equals("")){
                        progressBar.setVisibility(View.GONE);
                        Intent intent_developer = new Intent(MainActivity.this,main_home.class);
                        startActivity(intent_developer);
                    }else{


                        if(TextUtils.isEmpty(email)){
                            Toast.makeText(MainActivity.this, "Email id is empty", Toast.LENGTH_SHORT).show();
                            edit_log_email.requestFocus();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        if(TextUtils.isEmpty(password)){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                            edit_log_password.requestFocus();
                            return;
                        }

                        auth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                            // Save the login status to shared preferences when the user logs in
                                            loginPrefsEditor = loginPreferences.edit();
                                            loginPrefsEditor.putBoolean("isLoggedIn", true);
                                            loginPrefsEditor.apply();

                                            Intent intent_home_page = new Intent(MainActivity.this,main_home.class);
                                            startActivity(intent_home_page);
                                            finish();



                                        }else{
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    }

                }
            });

        }


        txt_log_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_registration = new Intent(MainActivity.this , registration_page.class);
                startActivity(intent_registration);
            }
        });



    }
}