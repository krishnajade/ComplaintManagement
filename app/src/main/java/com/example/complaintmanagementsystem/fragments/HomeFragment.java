package com.example.complaintmanagementsystem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.complaintmanagementsystem.R;
import com.example.complaintmanagementsystem.models.ComplaintResponse;
import com.example.complaintmanagementsystem.models.UserData;
import com.example.complaintmanagementsystem.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    // Define the argument key for userId
    private static final String ARG_USER_ID = "userId";
    private static String userId;
    private TextView totalComplaintsTextView;
    private TextView notProcessedYetTextView;
    private TextView inProcessTextView;
    private TextView closedTextView;
    private ApiService apiService;
    private Retrofit retrofit;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String userId) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the userId from the arguments bundle
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }

        UserData.getInstance().setUserId(userId);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://complaint.trifrnd.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        totalComplaintsTextView = view.findViewById(R.id.total_complaints_number);
        notProcessedYetTextView = view.findViewById(R.id.complaints_not_processed_number);
        inProcessTextView = view.findViewById(R.id.complaints_status_in_process_number);
        closedTextView = view.findViewById(R.id.complaints_closed_number);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        Call<List<ComplaintResponse>> call = apiService.getUserComplaints(userId);
        call.enqueue(new Callback<List<ComplaintResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ComplaintResponse>> call, @NonNull Response<List<ComplaintResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    ComplaintResponse complaintResponse = response.body().get(0);
                    // Pass the userId to the ComplaintHistoryFragment


                    String totalComplaints = complaintResponse.getTotalComplaints();
                    String notProcessedYet = complaintResponse.getNotProcessedYet();
                    String inProcess = complaintResponse.getInProcess();
                    String closed = complaintResponse.getClosed();

                    totalComplaintsTextView.setText(totalComplaints);
                    notProcessedYetTextView.setText(notProcessedYet);
                    inProcessTextView.setText(inProcess);
                    closedTextView.setText(closed);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ComplaintResponse>> call, @NonNull Throwable t) {
                // Handle failure
            }
        });}
}