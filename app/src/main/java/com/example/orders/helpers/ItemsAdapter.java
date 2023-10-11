package com.example.orders.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orders.CartActivity;
import com.example.orders.R;
import com.example.orders.models.Cart;
import com.example.orders.models.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private List<Item> items;
    private int layout;
    private Context context;

    public ItemsAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        items = objects;
        layout = resource;
    }


    @SuppressLint({"ViewHolder", "ResourceAsColor", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = layoutInflater.inflate(layout, null);


        TextView item_title = convertView.findViewById(R.id.item_tittle);
        TextView item_price = convertView.findViewById(R.id.item_price);
        TextView item_info = convertView.findViewById(R.id.item_info);
        ImageView item_img = convertView.findViewById(R.id.item_image);
        Button cart_btn = convertView.findViewById(R.id.cart_btn);


        if (item_title != null) {
            item_title.setText(items.get(position).getTittle());
        }
        if (item_img != null) {
            @SuppressLint("DiscouragedApi") int id = getContext().getResources().getIdentifier("drawable/" + items.get(position).getImg(), null, getContext().getPackageName());
            item_img.setImageResource(id);
        }
        if (item_price != null) {
            item_price.setText(items.get(position).getPrice()  + " $");
        }
        if (item_info != null) {
            item_info.setText(items.get(position).getInfo());
        }
        cart_btn.setOnClickListener(View -> {
            String item = items.get(position).getTittle();
            List<Cart> orders = (List<Cart>) JSONHelper.importFromJSON(context);
            boolean exist = false;
            if (orders == null) {
                orders = new ArrayList<>();
                orders.add(new Cart(item, 1));
               Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_LONG).show();

            } else {
                for (Cart el : orders) {
                    if (item.equals(el.getTittle())) {
                        el.setAmount(el.getAmount() + 1);
                        exist = true;
                        Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_LONG).show();
                        continue;
                    }
                }
                if (!exist) {
                    orders.add(new Cart(item, 1));
                    Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_LONG).show();
                }
            }
            JSONHelper.exportToJSON(context, orders);
        });

        return convertView;
    }

}
