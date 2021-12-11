package com.example.bloodtrustor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.UserDao;
import com.example.bloodtrustor.database.entities.User;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextView backbutton;
    private EditText loginEmail,loginPassword;
    private TextView forgotpassword;
    private Button btnLogin, btnSignup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        loginEmail = findViewById(R.id.txtEmail);
        loginPassword = findViewById(R.id.txtPassword);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(view -> {
            AppDatabase db = Connection.database(this);
            UserDao userDao = db.userDao();
            User user = userDao.login(loginEmail.getText().toString(), loginPassword.getText().toString());
            if (user != null) {
                Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_LONG).show();
                loginEmail.clearFocus();
                loginEmail.setText("");
            }
        });
    }
}