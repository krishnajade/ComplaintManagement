package com.example.complaintmanagementsystem.models;

import com.google.gson.annotations.SerializedName;

public class ComplaintResponse {
    @SerializedName("totalcomplaint")
    private String totalComplaints;

    @SerializedName("notprocessedyet")
    private String notProcessedYet;

    @SerializedName("inprocess")
    private String inProcess;

    @SerializedName("closed")
    private String closed;

    public String getTotalComplaints() {
        return totalComplaints;
    }

    public String getNotProcessedYet() {
        return notProcessedYet;
    }

    public String getInProcess() {
        return inProcess;
    }

    public String getClosed() {
        return closed;
    }
}

