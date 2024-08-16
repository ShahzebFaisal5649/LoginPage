package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    private String username; // Using username as the primary key

    private String passwordHash;

    // Constructor
    public User(@NonNull String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getter and Setter for username
    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    // Getter and Setter for passwordHash
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
