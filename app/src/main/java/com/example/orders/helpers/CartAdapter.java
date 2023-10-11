package com.example.orders.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orders.CartActivity;
import com.example.orders.ItemActivity;
import com.example.orders.R;
import com.example.orders.models.Cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private  List<Cart> cart_orders;
    private int layout;
    private Context context;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cart_orders = objects;
        layout = resource;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Animation SCALE = AnimationUtils.loadAnimation(context, R.anim.cart_btn_animation);

        convertView = layoutInflater.inflate(layout, null);

        TextView item_cart_tittle = convertView.findViewById(R.id.item_cart_tittle);
        TextView item_cart_amount = convertView.findViewById(R.id.item_cart_amount);
        ImageButton plus_btn = convertView.findViewById(R.id.plus_btn);
        ImageButton minus_btn = convertView.findViewById(R.id.minus_btn);
        ImageButton delete_btn = convertView.findViewById(R.id.delete_btn);


            Cart item = cart_orders.get(position);
            if(item != null) {
                if(item.getTittle() != null) {
                    item_cart_tittle.setText(item.getTittle());
                }
                item_cart_amount.setText(String.valueOf(item.getAmount()));
            }

            delete_btn.setOnLongClickListener(view -> {
                cart_orders.remove(position);
                JSONHelper.exportToJSON(context, cart_orders);
                CartActivity.reloadCart(context);
                Toast.makeText(context, "Товар удален", Toast.LENGTH_LONG).show();
                delete_btn.startAnimation(SCALE);
                return true;
            });

            minus_btn.setOnClickListener(view -> {
                boolean exist = false;
               for (Cart el : cart_orders) {
                   if (item.getTittle().equals(el.getTittle())) {
                       if (el.getAmount() > 1) {
                           el.setAmount(el.getAmount() -1);
                           exist = true;
                           continue;
                       }
                   }
                }
               if(exist) {
                   JSONHelper.exportToJSON(context, cart_orders);
                   CartActivity.reloadCart(context);
               }
            });

        plus_btn.setOnClickListener(view -> {
            boolean exist = false;
            for (Cart el : cart_orders) {
                if (item.getTittle().equals(el.getTittle())) {
                        el.setAmount(el.getAmount() +1);
                        exist = true;
                        continue;
                }
            }
            if(exist) {
                JSONHelper.exportToJSON(context, cart_orders);
                CartActivity.reloadCart(context);
            }
        });

        return convertView;
    }
}
