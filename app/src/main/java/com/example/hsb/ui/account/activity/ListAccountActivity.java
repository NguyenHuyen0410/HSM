package com.example.hsb.ui.account.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hsb.R;
import com.example.hsb.storage.SystemRoles;
import com.example.hsb.ui.employee.fragment.EmployeeProfileFragment;
import com.example.hsb.ui.home.fragment.HomeFragment;
import com.example.hsb.ui.account.fragment.AccountFragment;
//import com.example.hsb.ui.home_customer.fragment.HomeFragmentCustomer;
import com.example.hsb.ui.room.fragment.RoomFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListAccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private AccountFragment accountFragment;
    private EmployeeProfileFragment employeeProfileFragment;
    private RoomFragment  roomFragment;

    private static final String TAG_DASHBOARD = "homeFragment";
    private static final String TAG_ACCOUNT = "accountFragment";
    private static final String TAG_CATEGORY = "categoryFragment";
    private static final String TAG_SERVICE = "serviceFragment";
    private static final String TAG_PROFILE = "employeeProfileFragment";
    private static final String TAG_HISTORY = "historyFragment";
    private static final String TAG_ORDER = "orderFragment";
    private static final String TAG_ROOM = "roomFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        fragmentManager = getSupportFragmentManager();

        // Retrieve existing fragments by tag or create new instances if null
        homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(TAG_DASHBOARD);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        accountFragment = (AccountFragment) fragmentManager.findFragmentByTag(TAG_ACCOUNT);
        if (accountFragment == null) {
            accountFragment = new AccountFragment();
        }

        employeeProfileFragment = (EmployeeProfileFragment) fragmentManager.findFragmentByTag(TAG_PROFILE);
        if(employeeProfileFragment == null){
            employeeProfileFragment = new EmployeeProfileFragment();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        String currentRole = SystemRoles.MANAGER.getName();
        String currentRole = SystemRoles.RECEPTIONIST.getName();
//        String currentRole = SystemRoles.CUSTOMER.getName();
        if(currentRole.equals(SystemRoles.MANAGER.getName())){
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.nav_menu_manager);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.account) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                    return true;
                } else if (id == R.id.category) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                    return true;
                } else if (id == R.id.service) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                    return true;
                } else if (id == R.id.profile) {
                    switchFragment(employeeProfileFragment, TAG_PROFILE);
                    return true;
                }
                return false;
            });
        } else if(currentRole.equals(SystemRoles.RECEPTIONIST.getName())){
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.nav_menu_receptionist);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.order) {
                    switchFragment(homeFragment, TAG_DASHBOARD);
                    return true;
                } else if (id == R.id.room) {
                    switchFragment(roomFragment, TAG_ROOM);
                    return true;
                } else if (id == R.id.service) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                    return true;
                } else if (id == R.id.profile) {
                    switchFragment(employeeProfileFragment, TAG_PROFILE);
                    return true;
                }
                return false;
            });
        } else{
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.nav_menu_customer);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.history) {
                    switchFragment(homeFragment, TAG_DASHBOARD);
                    return true;
                }  else if (id == R.id.service) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                    return true;
                } else if (id == R.id.profile) {
                    switchFragment(employeeProfileFragment, TAG_PROFILE);
                    return true;
                }
                return false;
            });
        }
        // Set the initial fragment if none is selected
        if (savedInstanceState == null) {
            switchFragment(homeFragment, TAG_DASHBOARD); // Default to homeFragment
        }
    }

    private void switchFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Remove existing AccountFragment instances
        Fragment existingAccountFragment = fragmentManager.findFragmentByTag(TAG_ACCOUNT);
        Fragment existingProfileFragment = fragmentManager.findFragmentByTag(TAG_PROFILE);
        if (existingAccountFragment != null && existingAccountFragment != fragment) {
            transaction.remove(existingAccountFragment);
        } else if(existingProfileFragment != null && existingProfileFragment != fragment){
            transaction.remove(existingProfileFragment);
        }

        // Add or show the new fragment
        if (!fragment.isAdded()) {
            transaction.add(R.id.bottom_navigation_container, fragment, tag);
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.show(fragment).commitAllowingStateLoss(); // Use commitAllowingStateLoss for safer fragment transactions

        currentFragment = fragment;
    }
}