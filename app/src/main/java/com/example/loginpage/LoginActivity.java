package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity"; // Tag for logging
    private AppDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); // Executor for background tasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        EditText loginUsername = findViewById(R.id.login_username);
        EditText loginPassword = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);
        CheckBox showPasswordCheckbox = findViewById(R.id.show_password_checkbox); // Checkbox for password visibility

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();

        loginButton.setOnClickListener(v -> {
            String username = loginUsername.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            // Log inputs for debugging
            Log.d(TAG, "Attempting login with username: " + username);

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Execute database operation on a background thread
            executorService.execute(() -> {
                User user = db.userDao().getUser(username);

                runOnUiThread(() -> {
                    if (user != null && UserAuthentication.checkPassword(password, user.getPasswordHash())) {
                        // Login successful, redirect to DiaryEntryActivity
                        Intent intent = new Intent(LoginActivity.this, DiaryEntryActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish(); // Close the login activity
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Set up the checkbox for showing/hiding password
        showPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                loginPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            } else {
                // Hide password
                loginPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Move cursor to the end of the text
            loginPassword.setSelection(loginPassword.getText().length());
        });
    }
}
