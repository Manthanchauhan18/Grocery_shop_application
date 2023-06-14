package com.example.grocery_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocery_shop.ElegantNumberButton;
import com.example.grocery_shop.R;
import com.example.grocery_shop.model.Grocery;
import com.example.grocery_shop.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    private Context context;
    private List<Grocery> groceryList;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageView groceryImage = holder.card_img;
        TextView groceryName = holder.card_txt_name;
        TextView groceryPrice = holder.card_txt_price;

        ElegantNumberButton elegantNumberButton= holder.elegantNumberButton;
        Button decrementButton = holder.decrementButton;
        Button incrementButton = holder.incrementButton;


        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegantNumberButton.decrementNumber();
            }
        });

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegantNumberButton.incrementNumber();
            }
        });


        groceryName.setText(groceryList.get(position).getgName());
        groceryPrice.setText("Rs. "+groceryList.get(position).getPrice());

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
