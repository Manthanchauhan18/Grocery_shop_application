package com.example.grocery_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.grocery_shop.model.Grocery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddGrocery extends Activity {

    EditText etAdaddname,etAdaddprice,etAdaddamount;
    Button btAdaddimage,btAdadditem;
    ProgressBar pbAdadd;
    Uri imagePath;
    DatabaseReference dbRef;
    StorageReference storage,store;
    String gName;
    int price,amount;
    String imageUrl;
    String gid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        etAdaddname = findViewById(R.id.etAdaddname);
        etAdaddprice = findViewById(R.id.etAdaddprice);
        etAdaddamount = findViewById(R.id.etAdaddamount);

        btAdaddimage = findViewById(R.id.btAdaddimage);
        btAdadditem = findViewById(R.id.btAdadditem);

        pbAdadd = findViewById(R.id.pbAdadd);
        pbAdadd.setVisibility(ProgressBar.GONE);

        btAdaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);//for calling gallery
                startActivityForResult(gallery, 201);
            }
        });

        btAdadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pbAdadd.setVisibility(ProgressBar.VISIBLE);

                gName=etAdaddname.getText().toString();
                price=Integer.parseInt(etAdaddprice.getText().toString());
                amount=Integer.parseInt(etAdaddamount.getText().toString());

                dbRef= FirebaseDatabase.getInstance().getReference("grocery");
                storage= FirebaseStorage.getInstance().getReferenceFromUrl("gs://grocery-shop-5271c.appspot.com");

                store=storage.child(gName);
                gid=dbRef.push().getKey();




                store.putFile(imagePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {
                                        Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();

                                        Grocery g=new Grocery(gid,uri.toString(),gName,price,amount);

                                        dbRef.child(gid).setValue(g);


                                        Toast.makeText(getApplicationContext(),"Grocerry added successfuly",Toast.LENGTH_SHORT).show();

                                        pbAdadd.setVisibility(pbAdadd.GONE);

                                        etAdaddprice.setText("");
                                        etAdaddname.setText("");
                                        etAdaddamount.setText("");

                                    }
                                });
                            }
                        });


            }
        });

    }

    @Override
    public void onActivityResult(int reqCode,int result,Intent data)
    {
        if(result==RESULT_OK && reqCode==201)
        {
            imagePath = data.getData();

            Toast.makeText(getApplicationContext(),"Image Selected",Toast.LENGTH_SHORT).show();

        }


    }
}