package com.example.grocery_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocery_shop.ElegantNumberButton;
import com.example.grocery_shop.R;
import com.example.grocery_shop.model.Grocery;
import com.example.grocery_shop.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    private Context context;
    private List<Grocery> groceryList;
    int count;

    ArrayList<String> gPriceList;
    ArrayList<String> gNameList;

    private ElegantNumberButton elegantNumberButton;
    private Button decrementButton;
    private Button incrementButton;

    public GroceryAdapter(List<Grocery> groceryList, Context context,ArrayList<String> gPriceList,ArrayList<String> gNameList) {
        this.context = context;
        this.groceryList = groceryList;
        this.gNameList = gNameList;
        this.gPriceList = gPriceList;
    }

    @NonNull
    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ImageView groceryImage = holder.card_img;
        TextView groceryName = holder.card_txt_name;
        TextView groceryPrice = holder.card_txt_price;

        ElegantNumberButton elegantNumberButton= holder.elegantNumberButton;
        Button decrementButton = holder.decrementButton;
        Button incrementButton = holder.incrementButton;


        groceryName.setText(groceryList.get(position).getgName());
        groceryPrice.setText("Rs. "+groceryList.get(position).getPrice());

        // Initialize count using Integer.parseInt((String) elegantNumberButton.getText())
        count = Integer.parseInt((String) elegantNumberButton.getText());

        String s = String.valueOf(groceryList.get(position) != null ? groceryList.get(position).getAmount() : null);
        int stock;
        if (s != null) {
            try {
                stock = Integer.parseInt(s);

                incrementButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(count < stock){
                            count++;
                            elegantNumberButton.setText(String.valueOf(count));
                        }

                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(context, "product is out of stock", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "product having null stock", Toast.LENGTH_SHORT).show();
        }





        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 0){
                    count--;
                    elegantNumberButton.setText(String.valueOf(count));
                }

                int count = Integer.parseInt((String) elegantNumberButton.getText());

            }
        });

        Glide.with(context)
                .load(groceryList.get(position).getImageUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(groceryImage);

    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView card_img;
        TextView card_txt_name,card_txt_price;
        private ElegantNumberButton elegantNumberButton;
        private Button decrementButton;
        private Button incrementButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card_img = itemView.findViewById(R.id.card_img);
            card_txt_name = itemView.findViewById(R.id.card_txt_name);
            card_txt_price = itemView.findViewById(R.id.card_txt_price);

            elegantNumberButton = itemView.findViewById(R.id.elegant_number_button);
            decrementButton = itemView.findViewById(R.id.card_decrement_button);
            incrementButton = itemView.findViewById(R.id.card_increment_button);

        }
    }
}
