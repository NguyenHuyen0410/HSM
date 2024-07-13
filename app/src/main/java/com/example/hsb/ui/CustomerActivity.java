package com.example.hsb.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.R;
import com.example.hsb.storage.SystemRoles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentNavigator fragmentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer);

        fragmentNavigator = new FragmentNavigator(this, getSupportFragmentManager());
        bottomNavigationView = findViewById(R.id.bottom_customer_navigation_view);

        String currentRole = SystemRoles.CUSTOMER.getName();
        fragmentNavigator.setupBottomNavigation(bottomNavigationView, currentRole);
        fragmentNavigator.setInitialFragment(currentRole, savedInstanceState);
        }
}