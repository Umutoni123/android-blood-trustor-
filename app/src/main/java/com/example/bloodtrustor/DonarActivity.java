package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.bloodtrustor.adapters.GiversAdapter;
import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.DonarDao;

public class DonarActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar);
        btnAdd = findViewById(R.id.btnDonar);

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddDonarActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.list);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        AppDatabase db = Connection.database(this);
        DonarDao donarDao = db.donarDao();
        GiversAdapter giversAdapter = new GiversAdapter(donarDao.getAll());
        recyclerView.setAdapter(giversAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}