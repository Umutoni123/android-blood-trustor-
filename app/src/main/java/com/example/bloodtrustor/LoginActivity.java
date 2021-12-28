package com.example.bloodtrustor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.UserDao;
import com.example.bloodtrustor.database.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail,loginPassword;
    private Button btnLogin, btnSignup;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preferences.getBoolean("loggedIn", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        loginEmail = findViewById(R.id.txtEmail);
        loginPassword = findViewById(R.id.txtPassword);
        editor = preferences.edit();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(view -> {
            String url = "https://blood-trustor.herokuapp.com/api/v1/login";
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Login...");
            progress.setCancelable(true);
            progress.show();
            RequestQueue queue = Volley.newRequestQueue(this);

            Map<String, String> params = new HashMap<String, String>();
            params.put("password", loginPassword.getText().toString());
            params.put("email", loginEmail.getText().toString());

            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest rq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismiss();
                    Log.d("Response", response.toString());

                    try {
                        editor.putBoolean("loggedIn", true);
                        editor.putInt("userId", response.getInt("id"));
                        editor.putString("role", response.getString("role"));
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        });
    }
}