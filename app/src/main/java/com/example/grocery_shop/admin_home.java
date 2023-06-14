package com.example.grocery_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_home extends AppCompatActivity {

    Button btAdshow,btAdadd,btAddelete,btAdupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


                btAdshow=findViewById(R.id.btAdshow);
                btAdadd=findViewById(R.id.btAdadd);
                btAddelete=findViewById(R.id.btAddelete);
                btAdupdate=findViewById(R.id.btAdupdate);

                btAdshow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent admin_show_user=new Intent(getApplicationContext(),ShowUser.class);
                        startActivity(admin_show_user);
                    }
                });


                btAdadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent admin_add_grocery=new Intent(getApplicationContext(),AddGrocery.class);
                        startActivity(admin_add_grocery);
                    }
                });


                btAddelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent admin_delete_grocery=new Intent(getApplicationContext(),Deletegrocery.class);
                        startActivity(admin_delete_grocery);
                    }
                });
//
//
//                btAdupdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Intent ii7=new Intent(getApplicationContext(),UpdateGrocery.class);
//                        startActivity(ii7);
//                    }
//                });


    }
}