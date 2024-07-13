package com.example.hsb.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.R;
import com.example.hsb.storage.SystemRoles;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReceptionistActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentNavigator fragmentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_receptionist);

        fragmentNavigator = new FragmentNavigator(this, getSupportFragmentManager());
        bottomNavigationView = findViewById(R.id.receptionist_bottom_navigation);

        String currentRole = SystemRoles.RECEPTIONIST.getName();
        fragmentNavigator.setupBottomNavigation(bottomNavigationView, currentRole);
        fragmentNavigator.setInitialFragment(currentRole, savedInstanceState);
        }
}