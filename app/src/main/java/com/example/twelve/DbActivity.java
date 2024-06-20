package com.example.twelve;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DbActivity extends AppCompatActivity {

    private EditText editTextName, editTextPassword;
    private Button buttonInsert, buttonSearch, buttonUpdate;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_db);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStudent();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });
    }

    private void insertStudent() {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!name.isEmpty() && !password.isEmpty()) {
            boolean result = dbHelper.insertStudent(name, password);
            if (result) {
                showAlert("Success", "Student Inserted");
            } else {
                showAlert("Error", "Insertion Failed");
            }
        } else {
            showAlert("Error", "Please fill all fields");
        }
    }

    private void searchStudent() {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!name.isEmpty() && !password.isEmpty()) {
            Cursor cursor = dbHelper.searchStudent(name, password);
            if (cursor.moveToFirst()) {
                showAlert("Success", "Student Found: " + cursor.getString(cursor.getColumnIndexOrThrow("name")));
            } else {
                showAlert("Error", "Student Not Found");
            }
            cursor.close();
        } else {
            showAlert("Error", "Please fill all fields");
        }
    }

    private void updateStudent() {
        String name = editTextName.getText().toString().trim();
        String newPassword = editTextPassword.getText().toString().trim();

        if (!name.isEmpty() && !newPassword.isEmpty()) {
            boolean result = dbHelper.updateStudent(name, newPassword);
            if (result) {
                showAlert("Success", "Student Updated");
            } else {
                showAlert("Error", "Update Failed");
            }
        } else {
            showAlert("Error", "Please fill all fields");
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DbActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}