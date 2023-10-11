package com.example.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orders.helpers.ItemsAdapter;
import com.example.orders.helpers.JSONHelper;
import com.example.orders.models.Cart;
import com.example.orders.models.Category;
import com.example.orders.models.Item;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    public static String category_id;
    private TextView emptyGoods_label;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        emptyGoods_label = findViewById(R.id.emptyGoods_label);

        ListView listView = findViewById(R.id.item_listView);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://orders-7c144-default-rtdb.europe-west1.firebasedatabase.app/");
        final DatabaseReference TABLE = firebaseDatabase.getReference("Category");

        TABLE.child(category_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> list = new ArrayList<>();
                Category category = snapshot.getValue(Category.class);
                if (category.getItems() != null) {
                    list = category.getItems();
                    ItemsAdapter itemsAdapter = new ItemsAdapter(ItemActivity.this, R.layout.item_detail, list);
                    listView.setAdapter(itemsAdapter);
                } else {
                    emptyGoods_label.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemActivity.this, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_menu, menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, CartActivity.class));
        return true;
    }

}