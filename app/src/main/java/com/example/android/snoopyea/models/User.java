package com.example.android.snoopyea.models;

public class User {
    public String userName;
    public String userEmail;

    public User() {
    }

    public User(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
}
