package com.example.grocery_shop.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery_shop.R;
import com.example.grocery_shop.Razor_pay;
import com.example.grocery_shop.adapter.GroceryAdapter;
import com.example.grocery_shop.databinding.FragmentHomeBinding;
import com.example.grocery_shop.model.Grocery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment<FragmentHomeBinding> extends Fragment {

    ArrayList<Grocery> groceryList;
    RecyclerView recyclerView;
    Button btGroceryOrderConfirm;
    FirebaseDatabase db;
    DatabaseReference dbref;
    GroceryAdapter groceryAdapter;

    ArrayList<String> card_txt_name = new ArrayList<String>();
    ArrayList<String> card_txt_price = new ArrayList<String>();


    private FragmentHomeBinding binding;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);
        dbref = FirebaseDatabase.getInstance().getReference("grocery");

        recyclerView = root.findViewById(R.id.recycler_grocery_list);
        btGroceryOrderConfirm = root.findViewById(R.id.btGroceryOrderConfirm);


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        groceryList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grocery groceryModel = snapshot.getValue(Grocery.class);
                    groceryList.add(groceryModel);
                }
                recyclerView.setAdapter(new GroceryAdapter(groceryList, getActivity(),card_txt_name,card_txt_price));
//                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        btGroceryOrderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_razor_pay = new Intent(getActivity() , Razor_pay.class);
                startActivity(intent_razor_pay);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}