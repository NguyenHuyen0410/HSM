package com.example.hsb.ui.account.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hsb.R;
import com.example.hsb.ui.account.fragment.account_fragment.AccountFragment;
import com.example.hsb.ui.account.fragment.HomeFragment;
import com.example.hsb.ui.account.fragment.category_fragment.CategoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListAccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private AccountFragment accountFragment;
    private CategoryFragment categoryFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        categoryFragment = new CategoryFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    switchFragment(homeFragment);
                    return true;
                } else if (id == R.id.account) {
                    switchFragment(accountFragment);
                    return true;
                } else if (id == R.id.category) {
                    switchFragment(categoryFragment);
                    return true;
                }
                return false;
            }
        });

        // Set the initial fragment if none is selected
        if (savedInstanceState == null) {
            switchFragment(homeFragment); // Default to homeFragment
        }
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != currentFragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                transaction.add(R.id.bottom_navigation_container, fragment);
            }
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.show(fragment).commit();
            currentFragment = fragment;
        }
    }
}