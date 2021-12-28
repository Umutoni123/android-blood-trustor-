package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodtrustor.adapters.GiversAdapter;
import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.DonarDao;
import com.example.bloodtrustor.database.entities.Donar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            finish();
        });

        recyclerView = findViewById(R.id.list);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCancelable(true);
        progress.show();
        String url = "https://blood-trustor.herokuapp.com/api/v1/donners";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                progress.dismiss();

                try {
                    //getting the whole json object from the response
                    JSONArray contactArray = new JSONArray(response);
                    Log.d("Data ", contactArray.toString());

                    List<Donar> donarList = new ArrayList<>();
                    for (int i = 0; i < contactArray.length(); i++) {

                        JSONObject contactObject = contactArray.getJSONObject(i);

                        Donar donar = new Donar();

                        donar.id = contactObject.getInt("id");
                        donar.name = contactObject.getString("name");
                        donar.email = contactObject.getString("phoneNumber");
                        donar.address = contactObject.getString("address");
                        donar.age = contactObject.getInt("age");
                        donar.bloodGroup = contactObject.getString("bloodGroup");
                        donarList.add(donar);
                    }

                    GiversAdapter giversAdapter = new GiversAdapter(donarList, DonarActivity.this);
                    recyclerView.setAdapter(giversAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(DonarActivity.this, DividerItemDecoration.VERTICAL));

                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        //displaying the error in toast if occur
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error :",error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}