package com.example.loginpage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    // Method to insert a new user into the database
    @Insert
    void insert(User user);

    // Method to get a user from the database by username
    @Query("SELECT * FROM users WHERE username = :username")
    User getUser(String username);
}
