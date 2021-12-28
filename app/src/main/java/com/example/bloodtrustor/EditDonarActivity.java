package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.DonarDao;
import com.example.bloodtrustor.database.entities.Donar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditDonarActivity extends AppCompatActivity {

    Button btnBack, btnSave,btnDelete;
    EditText txtName, txtAge, txtPhone, txtAddress, txtGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donar);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, DonarActivity.class);
            startActivity(intent);
            finish();
        });

        txtName = findViewById(R.id.txtName);
        txtName.setText(Global.user.name);
        txtAddress = findViewById(R.id.txtAddress);
        txtAddress.setText(Global.user.address);
        txtAge = findViewById(R.id.txtAge);
        txtAge.setText(String.valueOf(Global.user.age));
        txtPhone = findViewById(R.id.txtPhone);
        txtPhone.setText(Global.user.email);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> edit());

        txtGroup = findViewById(R.id.txtgroup);
        txtGroup.setText(Global.user.bloodGroup);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(view -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ProgressDialog progress = new ProgressDialog(btnDelete.getContext());
                        progress.setTitle("Deleting");
                        progress.setMessage("Please wait...");
                        progress.setCancelable(true);
                        progress.show();

                        final String JSON_URL = "https://blood-trustor.herokuapp.com/api/v1/donners/"+Global.user.id;

                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, JSON_URL, new Response.Listener<String>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(String response) {
                                progress.dismiss();
                                Intent intent = new Intent(view.getContext(), DonarActivity.class);
                                startActivity(intent);
                                Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progress.dismiss();
                                        //displaying the error in toast if occur
                                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("Error :",error.toString());
                                    }
                                });
                        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                        requestQueue.add(stringRequest);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            };
            DialogBox.alertBox(btnDelete.getContext(), dialogClickListener, "Are you sure?");
        });
    }

    public void edit() {
        if (txtName.getText().toString().isEmpty()) {
            txtName.setError("Name is required");
        } else {
            AppDatabase db = Connection.database(this);
            DonarDao donarDao = db.donarDao();
            Donar donar = new Donar();
            donar.id = Global.user.id;
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
            } catch (Exception ignored) {
            }

            if (donar.age < 18) {
                txtAge.setError("You are under 18 age.");
                return;
            }

            String url = "https://blood-trustor.herokuapp.com/api/v1/donners";
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Signup...");
            progress.setCancelable(true);
            progress.show();
            RequestQueue queue = Volley.newRequestQueue(this);

            Map<String, String> params = new HashMap<String, String>();
            params.put("name", donar.name);
            params.put("address", donar.address);
            params.put("bloodGroup", donar.bloodGroup);
            params.put("email", donar.email);

            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest rq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismiss();
                    Log.d("Response", response.toString());
                    Toast.makeText(EditDonarActivity.this, "Updated", Toast.LENGTH_LONG).show();
                    txtName.clearFocus();
                    txtAge.setText("");
                    txtGroup.setText("");
                    txtAddress.setText("");
                    txtPhone.setText("");

                    Intent intent = new Intent(EditDonarActivity.this, DonarActivity.class);
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