package com.example.complaintmanagementsystem.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("calls.php?apicall=login")
    Call<String> login(@Field("userEmail") String username, @Field("password") String password);

}