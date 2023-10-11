package com.example.orders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button reg_btn, auth_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AuthActivity.getDefaults(MainActivity.this, "number") != null) {
            startActivity(new Intent(MainActivity.this, StoreActivity.class));
        }

        reg_btn = findViewById(R.id.save_btn);
        auth_btn = findViewById(R.id.auth_btn);

        reg_btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.btn_animation));
        auth_btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.btn_animation));

        reg_btn.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, RegActivity.class));
        });

        auth_btn.setOnClickListener(View -> {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        });
    }

}