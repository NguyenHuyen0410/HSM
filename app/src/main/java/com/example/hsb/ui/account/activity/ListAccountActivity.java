package com.example.hsb.ui.account.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hsb.R;
import com.example.hsb.ui.home.fragment.HomeFragment;
import com.example.hsb.ui.account.fragment.account_fragment.AccountFragment;
import com.example.hsb.ui.category.fragment.category_fragment.CategoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListAccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private AccountFragment accountFragment;
    private CategoryFragment categoryFragment;

    private static final String TAG_HOME = "homeFragment";
    private static final String TAG_ACCOUNT = "accountFragment";
    private static final String TAG_CATEGORY = "categoryFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        fragmentManager = getSupportFragmentManager();

        // Retrieve existing fragments by tag or create new instances if null
        homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(TAG_HOME);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        accountFragment = (AccountFragment) fragmentManager.findFragmentByTag(TAG_ACCOUNT);
        if (accountFragment == null) {
            accountFragment = new AccountFragment();
        }

        categoryFragment = (CategoryFragment) fragmentManager.findFragmentByTag(TAG_CATEGORY);
        if (categoryFragment == null) {
            categoryFragment = new CategoryFragment();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    switchFragment(homeFragment, TAG_HOME);
                    return true;
                } else if (id == R.id.account) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                    return true;
                } else if (id == R.id.category) {
                    switchFragment(categoryFragment, TAG_CATEGORY);
                    return true;
                }
                return false;
            }
        });

        // Set the initial fragment if none is selected
        if (savedInstanceState == null) {
            switchFragment(homeFragment, TAG_HOME); // Default to homeFragment
        }
    }

    private void switchFragment(Fragment fragment, String tag) {
        if (fragment != currentFragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                transaction.add(R.id.bottom_navigation_container, fragment, tag);
            }
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.show(fragment).commit();
            currentFragment = fragment;
        }
    }
}