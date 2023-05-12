package com.example.complaintmanagementsystem.services;

import com.example.complaintmanagementsystem.models.ComplaintHistoryResponse;
import com.example.complaintmanagementsystem.models.ComplaintResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("calls.php?apicall=login")
    Call<String> login(@Field("userEmail") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("calls.php?apicall=logcomplaint")
    Call<String> logComplaint(
            @Field("category") int category,
            @Field("subcategory") String subcategory,
            @Field("complaintType") String complaintType,
            @Field("state") String state,
            @Field("noc") String noc,
            @Field("complaintDetails") String complaintDetails,
            @Field("userId") String userId
    );
    @FormUrlEncoded
    @POST("calls.php?apicall=readuserid")
    Call<String> readUserId(@Field("userEmail") String userEmail);

    @FormUrlEncoded
    @POST("calls.php?apicall=usercomplaint")
    Call<List<ComplaintResponse>> getUserComplaints(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("calls.php?apicall=complhist")
    Call<List<ComplaintHistoryResponse>> getUserComplaintsHistory(@Field("userId") String userId);
}