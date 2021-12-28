package com.example.bloodtrustor;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText txtFirstName, txtLastName, txtRole, txtEmail, txtPassword;
    Button btnSignup, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraction);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtRole = findViewById(R.id.txtRole);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String role = txtRole.getText().toString();

        boolean isValid = true;

        if (firstName.isEmpty()) {
            txtFirstName.setError("First name can not be empty");
            isValid = false;
        }

        if (lastName.isEmpty()) {
            txtLastName.setError("Last name can not be empty");
            isValid = false;
        }

        if (email.isEmpty()) {
            txtEmail.setError("Email can not be empty");
            isValid = false;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Age can not be empty");
            isValid = false;
        }

        if (isValid) {
            User user = new User();
            user.email = email;
            user.password = password;
            user.firstName = firstName;
            user.lastName = lastName;
            user.role = role;
            String url = "https://blood-trustor.herokuapp.com/api/v1/users";
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Signup...");
            progress.setCancelable(true);
            progress.show();
            RequestQueue queue = Volley.newRequestQueue(this);

            Map<String, String> params = new HashMap<String, String>();
            params.put("firstName", firstName);
            params.put("lastName", lastName);
            params.put("role", role);
            params.put("password", password);
            params.put("email", email);

            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest rq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismiss();
                    Log.d("Response", response.toString());
                    Toast.makeText(getApplicationContext(), "Sign up successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
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
        } else {
            Toast.makeText(this, "Sign up failed", Toast.LENGTH_LONG).show();
        }
    }
}