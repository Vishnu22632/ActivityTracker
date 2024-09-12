package com.synergytech.tms.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Method to hash the password using bcrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Method to check if the provided password matches the hashed password
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
