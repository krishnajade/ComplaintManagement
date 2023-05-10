package com.example.complaintmanagementsystem.models;

public class Complaint {
    private int category;
    private String subcategory;
    private String complaintType;
    private String state;
    private String noc;
    private String complaintDetails;

    public Complaint(int category, String subcategory, String complaintType, String state, String noc, String complaintDetails) {
        this.category = category;
        this.subcategory = subcategory;
        this.complaintType = complaintType;
        this.state = state;
        this.noc = noc;
        this.complaintDetails = complaintDetails;
    }

    // Create getters and setters as needed
}

