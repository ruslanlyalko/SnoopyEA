package com.example.android.snoopyea.models;

public class Notification {

    public String message;
    public String author;
    public String date;

    public Notification() {
    }

    public Notification(String message, String author, String date) {
        this.message = message;
        this.author = author;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
