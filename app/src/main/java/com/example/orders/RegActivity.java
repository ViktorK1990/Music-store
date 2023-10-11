package com.example.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orders.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegActivity extends AppCompatActivity {

    private EditText phone_field, login_field, password_field;
    private Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        phone_field = findViewById(R.id.item_field);
        login_field = findViewById(R.id.img_field);
        password_field = findViewById(R.id.password_field);
        reg_btn = findViewById(R.id.save_btn);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://orders-7c144-default-rtdb.europe-west1.firebasedatabase.app/");
        final DatabaseReference TABLE = firebaseDatabase.getReference("User");

        reg_btn.setOnClickListener(View -> {
            TABLE.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(phone_field.getText().toString()).exists()) {
                        Toast.makeText(RegActivity.this, "Такой пользователь существует!", Toast.LENGTH_LONG).show();
                    } else {
                        User user = new User(login_field.getText().toString(), password_field.getText().toString());
                        TABLE.child(phone_field.getText().toString()).setValue(user);
                        startActivity(new Intent(RegActivity.this, MainActivity.class));
                        Toast.makeText(RegActivity.this, "Вы успешно зарегистрированы!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RegActivity.this, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}