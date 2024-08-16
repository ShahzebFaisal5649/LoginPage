package com.example.loginpage;

import org.mindrot.jbcrypt.BCrypt;

public class UserAuthentication {

    // Method to hash the password
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Method to check if the provided password matches the hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
