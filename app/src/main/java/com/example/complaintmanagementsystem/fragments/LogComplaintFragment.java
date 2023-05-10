package com.example.complaintmanagementsystem.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.complaintmanagementsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LogComplaintFragment extends Fragment {
    Spinner spinnerCategory,spinnerSection,spinnerComplaintType,spinnerState;
    private List<Category> categoryList;
    TextView textViewCategoryLabel,textViewComplaintTypeLabel,textViewStateLabel,textViewSectionLabel ;

    public LogComplaintFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_complaint, container, false);
        // Inflate the layout for this fragment
         spinnerCategory = view.findViewById(R.id.spinner_class);
         spinnerSection=view.findViewById(R.id.spinner_section);

        spinnerComplaintType= view.findViewById(R.id.spinner_complaint_type);
        spinnerState= view.findViewById(R.id.spinner_state);

        textViewCategoryLabel = view.findViewById(R.id.text_view_class_label);
        textViewComplaintTypeLabel = view.findViewById(R.id.text_view_complaint_type_label);
        textViewStateLabel = view.findViewById(R.id.text_view_state_label);
        textViewSectionLabel=view.findViewById(R.id.text_view_section_label);

        // Fetch the category data and populate the spinner
        fetchCategories();
        spinnerComplaintType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedComplaintType = parent.getItemAtPosition(position).toString();
                textViewComplaintTypeLabel.setText(selectedComplaintType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedState=parent.getItemAtPosition(position).toString();
            textViewStateLabel.setText(selectedState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
    private void fetchCategories() {
        AsyncTask<Void, Void, String> fetchCategoriesTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://complaint.trifrnd.in/api/calls.php?apicall=category");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream responseBody = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody));
                        StringBuilder responseBuilder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                        }

                        return responseBuilder.toString();
                    } else {
                        // Handle the error case
                        // Display an error message or retry the request
                    }

                    connection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    try {
                        JSONArray categoriesArray = new JSONArray(response);

                        // Create a list to store the category objects
                        categoryList = new ArrayList<>();

                        for (int i = 0; i < categoriesArray.length(); i++) {
                            JSONObject categoryObject = categoriesArray.getJSONObject(i);
                            String categoryId = categoryObject.getString("id");
                            String categoryName = categoryObject.getString("categoryName");
                            Category category = new Category(categoryId, categoryName);
                            categoryList.add(category);
                        }

                        // Create an adapter with category names and set it to the category spinner
                        List<String> categoryNames = getCategoryNames(categoryList);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_dropdown_item, categoryNames);
                        spinnerCategory.setAdapter(adapter);

                        // Set an item selected listener on the category spinner
                        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Category selectedCategory = categoryList.get(position);
                                String selectedCategoryId = selectedCategory.getId();
                                String selectedCategoryName = selectedCategory.getName();

                                // Set the selected category as the spinner's selected item
                                spinnerCategory.setSelection(position);

                                // Use the selected category ID and name as needed
                                // For example, you can store them in variables or pass them to other methods
                                textViewCategoryLabel.setText(selectedCategoryName);

                                // Fetch subcategories based on the selected category ID
                                fetchSubcategories(selectedCategoryId);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // Handle the case when nothing is selected
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle the exception
                    }
                }
            }
        };

        fetchCategoriesTask.execute();
    }

    private List<String> getCategoryNames(List<Category> categoryList) {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categoryList) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }

    private void fetchSubcategories(String selectedCategoryId) {
        AsyncTask<String, Void, String> fetchSubcategoriesTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... categoryIds) {
                try {
                    String categoryId = categoryIds[0];
                    String urlString = "https://complaint.trifrnd.in/api/calls.php?apicall=subcat";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Set the category ID as form data
                    String formData = "categoryid=" + categoryId;

                    connection.getOutputStream().write(formData.getBytes());

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream responseBody = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody));
                        StringBuilder responseBuilder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                        }

                        return responseBuilder.toString();
                    } else {
                        // Handle the error case
                        // Display an error message or retry the request
                    }

                    connection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                return null;
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    try {
                        JSONArray subcategoriesArray = new JSONArray(response);

                        // Create a list to store the subcategory names
                        List<String> subcategoryNames = new ArrayList<>();

                        for (int i = 0; i < subcategoriesArray.length(); i++) {
                            JSONObject subcategoryObject = subcategoriesArray.getJSONObject(i);
                            String subcategoryName = subcategoryObject.getString("subcategory");
                            subcategoryNames.add(subcategoryName);
                        }

                        // Create an adapter with subcategory names and set it to the section spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_dropdown_item, subcategoryNames);
                        spinnerSection.setAdapter(adapter);

                        spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedSection = parent.getItemAtPosition(position).toString();
                                textViewSectionLabel.setText(selectedSection);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // Handle the case when nothing is selected
                            }
                        });









                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle the exception
                    }
                }
            }
        };

        fetchSubcategoriesTask.execute(selectedCategoryId);
    }

    // Category class to store the ID and name
    private class Category {
        private String id;
        private String name;

        public Category(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}