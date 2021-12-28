package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.DonarDao;
import com.example.bloodtrustor.database.entities.Donar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddDonarActivity extends AppCompatActivity {

    EditText txtName, txtAge, txtPhone, txtAddress, txtGroup;
    Button btnSave, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donar);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtAge = findViewById(R.id.txtAge);
        txtPhone = findViewById(R.id.txtPhone);
        btnSave = findViewById(R.id.btnSave);
        txtGroup = findViewById(R.id.txtgroup);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, DonarActivity.class);
            startActivity(intent);
            finish();
        });

        btnSave.setOnClickListener(view -> add());
    }

    public void add() {
       if (txtName.getText().toString().isEmpty()) {
           txtName.setError("Name is required");
       } else {
           AppDatabase db = Connection.database(this);
           DonarDao donarDao = db.donarDao();
           Donar donar = new Donar();
           donar.name = txtName.getText().toString();
           donar.address = txtAddress.getText().toString();
           donar.email = txtPhone.getText().toString();
           donar.bloodGroup = txtGroup.getText().toString();

           if (txtAge.getText().toString().isEmpty()) {
               txtAge.setError("Age is required");
               return;
           }

           try {
               donar.age = Integer.parseInt(txtAge.getText().toString());
           }catch (Exception ignored) {}

           if (donar.age < 18) {
               txtAge.setError("You are under 18 age.");
               return;
           }

           String url = "https://blood-trustor.herokuapp.com/api/v1/donners";
           ProgressDialog progress = new ProgressDialog(this);
           progress.setMessage("adding...");
           progress.setCancelable(true);
           progress.show();
           RequestQueue queue = Volley.newRequestQueue(this);

           Map<String, Object> params = new HashMap<String, Object>();
           params.put("name", donar.name);
           params.put("address", donar.address);
           params.put("bloodGroup", donar.bloodGroup);
           params.put("phoneNumber", donar.email);
           params.put("age", donar.age);

           JSONObject jsonObject = new JSONObject(params);

           JsonObjectRequest rq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                   progress.dismiss();
                   Log.d("Response", response.toString());
                   Toast.makeText(AddDonarActivity.this, "Added", Toast.LENGTH_LONG).show();
                   txtName.clearFocus();
                   txtAge.setText("");
                   txtGroup.setText("");
                   txtAddress.setText("");
                   txtPhone.setText("");

                   Intent intent = new Intent(AddDonarActivity.this, DonarActivity.class);
                   startActivity(intent);
                   finish();
               }
           },
                   new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           //displaying the error in toast if occur
                           progress.dismiss();
                           Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                           Log.e("Error :",error.toString());
                       }
                   });
           queue.add(rq);
       }
    }
}