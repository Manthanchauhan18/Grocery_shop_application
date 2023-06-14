package com.example.grocery_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    TextView txt_grocery_mart;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txt_grocery_mart = findViewById(R.id.txt_grocery_mart);

        Animation animation = AnimationUtils.loadAnimation(splash_screen.this , R.anim.scale);
        txt_grocery_mart.startAnimation(animation);

        Intent intent_splash = new Intent(splash_screen.this , MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent_splash);
                finish();
            }
        },3000);

    }
}