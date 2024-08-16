package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText phoneEdit;
    private EditText addressEdit;
    private EditText newPhoneEdit;
    private EditText newAddressEdit;
    private TextView phoneDisplay;
    private TextView addressDisplay;
    private Button editButton;
    private Button saveButton;
    private Button addDetailsButton;
    private Button saveNewDetailsButton;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<String> historyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Initialize views
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");

        TextView usernameDisplay = findViewById(R.id.username_display);
        TextView emailDisplay = findViewById(R.id.email_display);
        phoneDisplay = findViewById(R.id.phone_display);
        addressDisplay = findViewById(R.id.address_display);
        phoneEdit = findViewById(R.id.phone_edit);
        addressEdit = findViewById(R.id.address_edit);
        newPhoneEdit = findViewById(R.id.new_phone_edit);
        newAddressEdit = findViewById(R.id.new_address_edit);
        editButton = findViewById(R.id.edit_phone_address_button);
        saveButton = findViewById(R.id.save_phone_address_button);
        addDetailsButton = findViewById(R.id.add_details_button);
        saveNewDetailsButton = findViewById(R.id.save_new_details_button);
        historyRecyclerView = findViewById(R.id.history_recycler_view);

        // Initialize history list and adapter
        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyList);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(historyAdapter);

        // Display user details
        usernameDisplay.setText("Username: " + username);
        emailDisplay.setText("Email: " + email);
        phoneDisplay.setText("Phone: " + phone);
        addressDisplay.setText("Address: " + address);

        // Handle edit button
        editButton.setOnClickListener(v -> {
            phoneEdit.setVisibility(View.VISIBLE);
            addressEdit.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);

            phoneEdit.setText(phone);
            addressEdit.setText(address);
        });

        // Handle save button
        saveButton.setOnClickListener(v -> {
            String newPhone = phoneEdit.getText().toString().trim();
            String newAddress = addressEdit.getText().toString().trim();

            if (isValidPhoneNumber(newPhone) && !newAddress.isEmpty()) {
                phoneDisplay.setText("Phone: " + newPhone);
                addressDisplay.setText("Address: " + newAddress);
                updateHistory("Updated Phone: " + newPhone + ", Address: " + newAddress);

                phoneEdit.setVisibility(View.GONE);
                addressEdit.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);

                Toast.makeText(UserDetailsActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                if (!isValidPhoneNumber(newPhone)) {
                    phoneEdit.setError("Invalid phone number. It should be 11 digits.");
                }
                if (newAddress.isEmpty()) {
                    addressEdit.setError("Address cannot be empty.");
                }
                Toast.makeText(UserDetailsActivity.this, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle add new details button
        addDetailsButton.setOnClickListener(v -> {
            newPhoneEdit.setVisibility(View.VISIBLE);
            newAddressEdit.setVisibility(View.VISIBLE);
            saveNewDetailsButton.setVisibility(View.VISIBLE);
            addDetailsButton.setVisibility(View.GONE);
        });

        // Handle save new details button
        saveNewDetailsButton.setOnClickListener(v -> {
            String newPhone = newPhoneEdit.getText().toString().trim();
            String newAddress = newAddressEdit.getText().toString().trim();

            if (isValidPhoneNumber(newPhone) && !newAddress.isEmpty()) {
                phoneDisplay.setText("Phone: " + newPhone);
                addressDisplay.setText("Address: " + newAddress);
                updateHistory("Added New Phone: " + newPhone + ", Address: " + newAddress);

                newPhoneEdit.setVisibility(View.GONE);
                newAddressEdit.setVisibility(View.GONE);
                saveNewDetailsButton.setVisibility(View.GONE);
                addDetailsButton.setVisibility(View.VISIBLE);

                Toast.makeText(UserDetailsActivity.this, "New details added successfully", Toast.LENGTH_SHORT).show();
            } else {
                if (!isValidPhoneNumber(newPhone)) {
                    newPhoneEdit.setError("Invalid phone number. It should be 11 digits.");
                }
                if (newAddress.isEmpty()) {
                    newAddressEdit.setError("Address cannot be empty.");
                }
                Toast.makeText(UserDetailsActivity.this, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Logout button
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{11}");
    }

    private void updateHistory(String change) {
        historyList.add(change);
        historyAdapter.notifyDataSetChanged();
    }
}
