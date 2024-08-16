package com.example.loginpage;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DiaryEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);

        // Get the username from the Intent
        String username = getIntent().getStringExtra("username");

        // Example usage of the username
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome, " + username + "!");
    }
}
