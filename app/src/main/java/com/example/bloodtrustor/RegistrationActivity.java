package com.example.bloodtrustor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.UserDao;
import com.example.bloodtrustor.database.entities.User;

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
            AppDatabase db = Connection.database(this);
            User user = new User();
            user.email = email;
            user.password = password;
            user.firstName = firstName;
            user.lastName = lastName;
            user.role = role;

            UserDao userDao = db.userDao();
            userDao.insertAll(user);
            Toast.makeText(this, "Sign up successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Sign up failed", Toast.LENGTH_LONG).show();
        }
    }
}