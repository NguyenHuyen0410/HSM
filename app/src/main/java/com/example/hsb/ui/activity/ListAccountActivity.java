package com.example.hsb.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.R;
import com.example.hsb.ui.fragment.AccountFragment;
import com.example.hsb.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ListAccountActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_account);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_container, homeFragment).commit();
                    return true;
                }
                if(id == R.id.account){
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_container, accountFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}