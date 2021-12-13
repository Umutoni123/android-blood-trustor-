package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DonarActivity extends AppCompatActivity {

    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar);
        btnAdd = findViewById(R.id.btnDonar);

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddDonarActivity.class);
            startActivity(intent);
        });
    }
}