package com.example.bloodtrustor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodtrustor.database.AppDatabase;
import com.example.bloodtrustor.database.Connection;
import com.example.bloodtrustor.database.dao.DonarDao;
import com.example.bloodtrustor.database.entities.Donar;

public class EditDonarActivity extends AppCompatActivity {

    Button btnBack, btnSave,btnDelete;
    EditText txtName, txtAge, txtPhone, txtAddress, txtGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donar);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddDonarActivity.class);
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
            AppDatabase db = Connection.database(this);
            DonarDao donarDao = db.donarDao();
            donarDao.deleteDonar(Global.user.id);
            Intent intent = new Intent(this, DonarActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
            finish();
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

            donarDao.update(donar.name, donar.email, donar.address, donar.bloodGroup, donar.age, donar.id);
            Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
            txtName.clearFocus();
            txtAge.setText("");
            txtGroup.setText("");
            txtAddress.setText("");
            txtPhone.setText("");

            Intent intent = new Intent(this, DonarActivity.class);
            startActivity(intent);
            finish();
        }
    }
}