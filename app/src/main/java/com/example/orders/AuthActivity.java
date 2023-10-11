package com.example.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orders.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthActivity extends AppCompatActivity {

    private EditText phone_field, password_field;
    private Button entry_btn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        phone_field = findViewById(R.id.item_field);
        password_field = findViewById(R.id.password_field);
        entry_btn = findViewById(R.id.entry_btn);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://orders-7c144-default-rtdb.europe-west1.firebasedatabase.app/");
        final DatabaseReference TABLE = firebaseDatabase.getReference("User");

        entry_btn.setOnClickListener(View -> {
            TABLE.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(phone_field.getText().toString().equals("ADMIN") && password_field.getText().toString().equals("ADMIN")) {
                        startActivity(new Intent(AuthActivity.this, AdminPage.class));
                    }
                    if (snapshot.child(phone_field.getText().toString()).exists()) {
                        User user = snapshot.child(phone_field.getText().toString()).getValue(User.class);
                        auth(user);
                    } else {
                        Toast.makeText(AuthActivity.this, "Такой пользователь не существует!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AuthActivity.this, "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void auth(User user) {
        System.out.println(user.getPassword());
        if (user.getPassword().equals(password_field.getText().toString())) {
            Toast.makeText(AuthActivity.this, "Авторизация прошла успешно!", Toast.LENGTH_LONG).show();
            setDefaults(AuthActivity.this, "number", phone_field.getText().toString());
            setDefaults(AuthActivity.this, "name", user.getName());
            startActivity(new Intent(AuthActivity.this, StoreActivity.class));
        } else {
            Toast.makeText(AuthActivity.this, "Неверный пароль!", Toast.LENGTH_LONG).show();
        }
    }

    public static void setDefaults(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDefaults(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }
}