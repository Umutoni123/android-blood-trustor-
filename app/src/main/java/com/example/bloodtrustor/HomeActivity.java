package com.example.bloodtrustor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button btnGivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = preferences.edit();
        setContentView(R.layout.activity_home);
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            editor.remove("loggedIn");
            editor.apply();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnGivers = findViewById(R.id.btnGivers);
        if (preferences.getString("role", "user").equalsIgnoreCase("admin")) {
            btnGivers.setVisibility(View.VISIBLE);
            btnGivers.setOnClickListener(view -> {
                Intent intent = new Intent(this, DonarActivity.class);
                startActivity(intent);
            });
        }
    }
}