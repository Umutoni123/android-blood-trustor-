package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class EditDonarActivity extends AppCompatActivity {

    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donar);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());
    }
}