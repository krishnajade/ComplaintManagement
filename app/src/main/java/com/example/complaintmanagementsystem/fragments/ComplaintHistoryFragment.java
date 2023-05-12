package com.example.complaintmanagementsystem.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.complaintmanagementsystem.R;
import com.example.complaintmanagementsystem.models.ComplaintHistoryResponse;
import com.example.complaintmanagementsystem.models.UserData;
import com.example.complaintmanagementsystem.services.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComplaintHistoryFragment extends Fragment {
    private static final String ARG_USER_ID = "userId";
    private static String userId = UserData.getInstance().getUserId();
    private ApiService apiService;
    private Retrofit retrofit;
    private TableLayout complaintTable;

    public ComplaintHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://complaint.trifrnd.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_history, container, false);

        complaintTable = view.findViewById(R.id.complaint_table);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Call<List<ComplaintHistoryResponse>> call = apiService.getUserComplaintsHistory(userId);
        call.enqueue(new Callback<List<ComplaintHistoryResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ComplaintHistoryResponse>> call, @NonNull Response<List<ComplaintHistoryResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<ComplaintHistoryResponse> complaintList = response.body();

                    for (ComplaintHistoryResponse complaint : complaintList) {
                        addTableRow(complaint);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ComplaintHistoryResponse>> call, @NonNull Throwable t) {
                // Handle failure
            }
        });
    }
    private void addTableRow(ComplaintHistoryResponse complaint) {
        TableRow row = new TableRow(requireContext());

        TextView complaintNumberTextView = new TextView(requireContext());
        complaintNumberTextView.setText(complaint.getComplaintNumber());
        TableRow.LayoutParams complaintNumberParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
        complaintNumberTextView.setLayoutParams(complaintNumberParams);
        row.addView(complaintNumberTextView);

        TextView regDateTextView = new TextView(requireContext());
        regDateTextView.setText(complaint.getRegDate());
        TableRow.LayoutParams regDateParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f);
        regDateParams.setMargins(10, 0, 0, 0); // Add left margin of 10dp
        regDateTextView.setLayoutParams(regDateParams);
        row.addView(regDateTextView);

        TextView lastUpdationDateTextView = new TextView(requireContext());
        lastUpdationDateTextView.setText(complaint.getLastUpdationDate());
        TableRow.LayoutParams lastUpdationDateParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f);
        lastUpdationDateTextView.setLayoutParams(lastUpdationDateParams);
        row.addView(lastUpdationDateTextView);

        TextView statusTextView = new TextView(requireContext());
        statusTextView.setText(complaint.getStatus());
        TableRow.LayoutParams statusParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
        statusTextView.setLayoutParams(statusParams);
        row.addView(statusTextView);

        complaintTable.addView(row);
    }
}