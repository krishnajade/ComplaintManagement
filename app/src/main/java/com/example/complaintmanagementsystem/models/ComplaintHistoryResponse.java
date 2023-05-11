package com.example.complaintmanagementsystem.models;

import com.google.gson.annotations.SerializedName;

public class ComplaintHistoryResponse {
    @SerializedName("complaintNumber")
    private String complaintNumber;

    @SerializedName("regDate")
    private String regDate;

    @SerializedName("lastUpdationDate")
    private String lastUpdationDate;

    @SerializedName("status")
    private String status;

    public String getComplaintNumber() {
        return complaintNumber;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getLastUpdationDate() {
        return lastUpdationDate;
    }

    public String getStatus() {
        return status;
    }
}
