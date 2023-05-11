package com.example.complaintmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.complaintmanagementsystem.fragments.ComplaintHistoryFragment;
import com.example.complaintmanagementsystem.fragments.HomeFragment;
import com.example.complaintmanagementsystem.fragments.LogComplaintFragment;
import com.example.complaintmanagementsystem.services.ApiService;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActiveUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_user);
        setTitle("Dashboard");

        String username = getIntent().getStringExtra("username");
        // Create an instance of Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://complaint.trifrnd.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the ApiService
        apiService = retrofit.create(ApiService.class);

        if (username != null && !username.isEmpty()) {
            // Call the API endpoint
            callApiEndpoint(username);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
    private void callApiEndpoint(String userEmail) {
        // Make the API call
        apiService.readUserId(userEmail).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String userId = response.body();

                    // Use the userId as needed
                    // Pass userId as argument to HomeFragment
                    HomeFragment homeFragment = HomeFragment.newInstance(userId);

                    // Replace the fragment container with HomeFragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            
                            .commit();
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Handle the failure case
                // ...
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_log_complaint:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogComplaintFragment()).commit();
                break;
            case R.id.nav_complaint_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ComplaintHistoryFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}