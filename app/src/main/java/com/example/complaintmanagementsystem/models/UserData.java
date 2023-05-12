package com.example.complaintmanagementsystem.models;

public class UserData {
    private static UserData instance;
    private String userId;

    private UserData() {
        // private constructor to prevent instantiation
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}