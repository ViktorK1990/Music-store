package com.example.orders;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.orders.helpers.CartAdapter;
import com.example.orders.helpers.JSONHelper;
import com.example.orders.models.Cart;
import com.example.orders.models.Orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {

    public static List<Cart> cart_orders = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static ListView cart_view;
    @SuppressLint("StaticFieldLeak")
    private static TextView cart_label;
    @SuppressLint("StaticFieldLeak")
    private static Button clear_btn;
    @SuppressLint("StaticFieldLeak")
    private static Button total_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart_label = findViewById(R.id.cart_label);
        clear_btn = findViewById(R.id.clear_btn);
        total_btn = findViewById(R.id.total_btn);
        cart_view = findViewById(R.id.cart_view);
        loadCart();

        clear_btn.setOnClickListener(view -> {
            cart_orders = new ArrayList<>();
            JSONHelper.exportToJSON(this, cart_orders);
            loadCart();
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://orders-7c144-default-rtdb.europe-west1.firebasedatabase.app/");
        final DatabaseReference TABLE = firebaseDatabase.getReference("Orders");


        total_btn.setOnClickListener(View -> {
            TABLE.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long orderDate = System.currentTimeMillis() / 1000;
                    String name = AuthActivity.getDefaults(CartActivity.this, "name");
                    String phone = AuthActivity.getDefaults(CartActivity.this, "number");
                    String value = createOrder();
                    System.out.println(value);
                    Orders importOrder = new Orders(phone, name, value);
                    System.out.println(importOrder.getOrders());
                    TABLE.child(Long.toString(orderDate)).setValue(importOrder);
                    Toast.makeText(CartActivity.this, "Заказ успешно сформирован", Toast.LENGTH_LONG).show();
                    cart_orders.clear();
                    JSONHelper.exportToJSON(CartActivity.this, cart_orders);
                    loadCart();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CartActivity.this, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
        });
    }


    public void loadCart() {
        cart_orders = (List<Cart>) JSONHelper.importFromJSON(this);
        if (cart_orders == null) {
            cart_view.setVisibility(View.INVISIBLE);
            clear_btn.setVisibility(View.INVISIBLE);
            total_btn.setVisibility(View.INVISIBLE);
            cart_label.setText("Вы не добавили ни одного товара!");
        } else {
            CartAdapter cartAdapter = new CartAdapter(CartActivity.this, R.layout.cart_item_view, cart_orders);
            cart_view.setAdapter(cartAdapter);
        }
    }

    public static void reloadCart(Context context) {
        cart_orders = (List<Cart>) JSONHelper.importFromJSON(context);
        if (cart_orders == null) {
            cart_view.setVisibility(View.INVISIBLE);
            clear_btn.setVisibility(View.INVISIBLE);
            total_btn.setVisibility(View.INVISIBLE);
            cart_label.setText("Вы не добавили ни одного товара!");
        } else {
            CartAdapter cartAdapter = new CartAdapter(context, R.layout.cart_item_view, cart_orders);
            cart_view.setAdapter(cartAdapter);
        }
    }


    public String createOrder() {
        List<Cart> orders = (List<Cart>) JSONHelper.importFromJSON(this);
        StringBuilder stringBuilder = new StringBuilder();
        assert orders != null;
        for (Cart el : orders) {
           stringBuilder.append(el.toString()).append("   ");
        }
        return String.valueOf(stringBuilder);
    }
}