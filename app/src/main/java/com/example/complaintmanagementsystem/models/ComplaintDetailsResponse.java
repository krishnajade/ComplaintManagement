package com.example.complaintmanagementsystem.models;

import com.google.gson.annotations.SerializedName;

public class ComplaintDetailsResponse {
    @SerializedName("complaintNumber")
    private String complaintNumber;

    @SerializedName("userId")
    private String userId;

    @SerializedName("category")
    private String category;

    @SerializedName("subcategory")
    private String subcategory;

    @SerializedName("complaintType")
    private String complaintType;

    @SerializedName("state")
    private String state;

    @SerializedName("noc")
    private String noc;

    @SerializedName("complaintDetails")
    private String complaintDetails;

    @SerializedName("complaintFile")
    private String complaintFile;

    @SerializedName("regDate")
    private String regDate;

    @SerializedName("status")
    private String status;

    @SerializedName("lastUpdationDate")
    private String lastUpdationDate;

    // Add getters and setters for the fields

    public String getComplaintNumber() {
        return complaintNumber;
    }

    public void setComplaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNoc() {
        return noc;
    }

    public void setNoc(String noc) {
        this.noc = noc;
    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    public String getComplaintFile() {
        return complaintFile;
    }

    public void setComplaintFile(String complaintFile) {
        this.complaintFile = complaintFile;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdationDate() {
        return lastUpdationDate;
    }

    public void setLastUpdationDate(String lastUpdationDate) {
        this.lastUpdationDate = lastUpdationDate;
    }
}

