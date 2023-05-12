package com.example.complaintmanagementsystem.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.complaintmanagementsystem.R;
import com.example.complaintmanagementsystem.models.ComplaintDetailsResponse;
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

        TextView actionTextView = new TextView(requireContext());
        actionTextView.setText("view");
        actionTextView.setTextColor(Color.BLUE); // Set the text color to indicate it's clickable
        TableRow.LayoutParams actionParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
        actionTextView.setLayoutParams(actionParams);
        row.addView(actionTextView);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://complaint.trifrnd.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        actionTextView.setOnClickListener(view -> {
            String complaintNumber = complaint.getComplaintNumber();

            // Make the API call to retrieve complaint details
            Call<List<ComplaintDetailsResponse>> call = apiService.getComplaintDetails(complaintNumber);
            call.enqueue(new Callback<List<ComplaintDetailsResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ComplaintDetailsResponse>> call, @NonNull Response<List<ComplaintDetailsResponse>> response) {
                    if (response.isSuccessful()) {
                        List<ComplaintDetailsResponse> complaintDetails = response.body();

                        //ComplaintDetailsResponse complaintDetails = response.body();
                        // Handle the received complaint details
                        // e.g., show them in a dialog or navigate to a new activity
                        if (complaintDetails != null) {
                            String details = "Complaint Number: " + complaintDetails.get(0).getComplaintNumber() + "\n"
                                    + "Category: " + complaintDetails.get(0).getCategory() + "\n"
                                    + "Subcategory: " + complaintDetails.get(0).getSubcategory() + "\n"
                                    + "Complaint Type: " + complaintDetails.get(0).getComplaintType() + "\n"
                                    + "State: " + complaintDetails.get(0).getState() + "\n"
                                    + "NOC: " + complaintDetails.get(0).getNoc() + "\n"
                                    + "Complaint Details: " + complaintDetails.get(0).getComplaintDetails() + "\n"
                                    + "Complaint File: " + complaintDetails.get(0).getComplaintFile() + "\n"
                                    + "Registered Date: " + complaintDetails.get(0).getRegDate() + "\n"
                                    + "Status: " + complaintDetails.get(0).getStatus() + "\n"
                                    + "Last Updation Date: " + complaintDetails.get(0).getLastUpdationDate();
                            // Show the details in a dialog or navigate to a new activity
                            // Example using a dialog:
                            showDialog("Complaint Details", details);
                        }
                    } else {
                        // Handle API error
                        showDialog("Error", "Failed to retrieve complaint details");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ComplaintDetailsResponse>> call, @NonNull Throwable t) {
                    // Handle network or request failure
                    showDialog("Error", "Network or request failure");
                }
            });
        });

        complaintTable.addView(row);
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);

        // Inflate the custom dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complaint_details, null);
        builder.setView(dialogView);

        // Set the message in the dialog
        TextView detailsTextView = dialogView.findViewById(R.id.detailsTextView);
        detailsTextView.setText(message);

        // Set the OK button
        builder.setPositiveButton("OK", null);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}