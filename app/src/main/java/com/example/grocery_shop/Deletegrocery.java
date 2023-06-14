package com.example.grocery_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grocery_shop.model.Grocery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Deletegrocery extends AppCompatActivity {

    EditText etAdDelname, etAdDelprice;
    Button btAdDelgrocery;
    ProgressBar pbAdDel;
    Spinner spAdDel;

    List<String> listName = new ArrayList<String>();
    List<Grocery> listObj = new ArrayList<Grocery>();

    DatabaseReference dbRef;
    StorageReference storage;

    String gid, gUrl, gName, measure;
    int price, stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletegrocery);


            etAdDelname = findViewById(R.id.etAdDelname);
            etAdDelprice = findViewById(R.id.etAdDelprice);

            btAdDelgrocery = findViewById(R.id.btAdDelgrocery);

            pbAdDel = findViewById(R.id.PbAdDel);
            pbAdDel.setVisibility(ProgressBar.GONE);

            spAdDel = findViewById(R.id.SpAdDel);

            dbRef = FirebaseDatabase.getInstance().getReference("grocery");
            storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://grocery-shop-5271c.appspot.com");

            dbRef.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    listName.clear();
                    listObj.clear();

                    for(DataSnapshot shot : dataSnapshot.getChildren())
                    {
                        Grocery grocery = shot.getValue(Grocery.class);

                        listName.add(grocery.getgName());
                        listObj.add(grocery);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listName);
                    spAdDel.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            spAdDel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    gid = listObj.get(position).getGid();
                    gName = listName.get(position); // listObj.get(position).getgName();
                    gUrl = listObj.get(position).getImageUrl();
                    price = listObj.get(position).getPrice();


                    etAdDelname.setText("" + listName.get(position));
                    etAdDelprice.setText("" + price);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

            btAdDelgrocery.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    pbAdDel.setVisibility(ProgressBar.VISIBLE);
                    dbRef = FirebaseDatabase.getInstance().getReference("grocery");
                    storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://grocery-shop-5271c.appspot.com");

                    Query delQ = dbRef.orderByChild("gid").equalTo(gid);

                    delQ.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot snap : dataSnapshot.getChildren())
                            {
                                Grocery g1 = snap.getValue(Grocery.class);

                                snap.getRef().removeValue();            // used to remove record from realtimeDatabase

                                storage = storage.child(gName);

                                storage.delete().addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        Toast.makeText(getApplicationContext(), gName + " has been removed successfully" , Toast.LENGTH_LONG).show();
                                        pbAdDel.setVisibility(ProgressBar.GONE);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });

                }
            });



    }
}