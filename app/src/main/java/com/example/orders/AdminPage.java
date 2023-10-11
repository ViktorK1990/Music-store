package com.example.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orders.models.Category;
import com.example.orders.models.Item;
import com.example.orders.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends AppCompatActivity {
    private EditText item_field, img_field, id_field, item_name_field, item_img_field, item_price_field, item_info_field;
    private Button save_btn, add_btn, saveAll_btn;
    private TextView admin_label, add_item_label;
    private List<Item> items = new ArrayList<>();

    private String id, categoryType, img;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        item_field = findViewById(R.id.item_field);
        img_field = findViewById(R.id.img_field);
        id_field = findViewById(R.id.id_field);
        item_name_field = findViewById(R.id.item_name_field);
        item_img_field = findViewById(R.id.item_img_field);
        item_price_field = findViewById(R.id.item_price_field);
        item_info_field = findViewById(R.id.item_info_field);
        save_btn = findViewById(R.id.save_btn);
        add_btn = findViewById(R.id.add_btn);
        saveAll_btn = findViewById(R.id.saveAll_btn);
        add_item_label = findViewById(R.id.add_item_label);
        admin_label = findViewById(R.id.admin_label);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://orders-7c144-default-rtdb.europe-west1.firebasedatabase.app/");
        final DatabaseReference TABLE = firebaseDatabase.getReference("Category");

        save_btn.setOnClickListener(View -> {
            id = id_field.getText().toString();
            categoryType = item_field.getText().toString();
            img = img_field.getText().toString();
            createItems();
        });

        saveAll_btn.setOnClickListener(View -> {
            TABLE.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Category category = new Category(id ,categoryType, img, items);
                    TABLE.child(id_field.getText().toString()).setValue(category);
                    Toast.makeText(AdminPage.this, "Товар добавлен", Toast.LENGTH_LONG).show();
                    recreate();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdminPage.this, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void createItems() {

        admin_label.setVisibility(View.INVISIBLE);
        id_field.setVisibility(View.INVISIBLE);
        item_field.setVisibility(View.INVISIBLE);
        img_field.setVisibility(View.INVISIBLE);
        save_btn.setVisibility(View.INVISIBLE);

        add_item_label.setVisibility(View.VISIBLE);
        item_name_field.setVisibility(View.VISIBLE);
        item_price_field.setVisibility(View.VISIBLE);
        item_img_field.setVisibility(View.VISIBLE);
        item_info_field.setVisibility(View.VISIBLE);
        add_btn.setVisibility(View.VISIBLE);
        saveAll_btn.setVisibility(View.VISIBLE);

        add_btn.setOnClickListener(View -> {
            items.add(new Item(item_name_field.getText().toString(), item_price_field.getText().toString(), item_info_field.getText().toString(), item_img_field.getText().toString()));

            item_name_field.setText("");
            item_price_field.setText("");
            item_img_field.setText("");
            item_info_field.setText("");
        });
    }
}