package com.example.hsb.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hsb.R;
import com.example.hsb.storage.SystemRoles;
import com.example.hsb.ui.account.fragment.AccountFragment;
import com.example.hsb.ui.category.fragment.CategoryFragment;
import com.example.hsb.ui.customer_history.fragment.ServiceHistoryCustomerFragment;
import com.example.hsb.ui.employee.fragment.EmployeeProfileFragment;
import com.example.hsb.ui.history.fragment.ServiceHistoryFragment;
import com.example.hsb.ui.home.fragment.HomeFragment;
import com.example.hsb.ui.home_customer.fragment.HomeFragmentCustomer;
import com.example.hsb.ui.room.fragment.RoomFragment;
import com.example.hsb.ui.service.fragment.ServiceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentNavigator {
    private final Context context;
    private final FragmentManager fragmentManager;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private HomeFragmentCustomer homeFragmentCustomer;
    private AccountFragment accountFragment;
    private EmployeeProfileFragment employeeProfileFragment;
    private RoomFragment roomFragment;
    private ServiceHistoryFragment serviceHistoryFragment;
    private ServiceHistoryCustomerFragment serviceHistoryCustomerFragment;
    private CategoryFragment categoryFragment;
    private ServiceFragment serviceFragment;
    private static final String TAG_HOME = "homeFragment";
    private static final String TAG_HOME_CUSTOMER = "fragmentHomeCustomer";
    private static final String TAG_ACCOUNT = "accountFragment";
    private static final String TAG_PROFILE = "employeeProfileFragment";
    private static final String TAG_HISTORY_CUSTOMER = "historyCustomerFragment";
    private static final String TAG_HISTORY = "historyFragment";
    private static final String TAG_ROOM = "roomFragment";
    private static final String TAG_CATEGORY = "categoryFragment";
    private static final String TAG_SERVICE = "serviceFragment";

    public FragmentNavigator(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        initializeFragments();
    }

    private void initializeFragments() {
        homeFragment = getOrCreateFragment(HomeFragment.class, TAG_HOME);
        homeFragmentCustomer = getOrCreateFragment(HomeFragmentCustomer.class, TAG_HOME_CUSTOMER);
        accountFragment = getOrCreateFragment(AccountFragment.class, TAG_ACCOUNT);
        employeeProfileFragment = getOrCreateFragment(EmployeeProfileFragment.class, TAG_PROFILE);
        roomFragment = getOrCreateFragment(RoomFragment.class, TAG_ROOM);
        serviceHistoryFragment = getOrCreateFragment(ServiceHistoryFragment.class, TAG_HISTORY);
        serviceHistoryCustomerFragment = getOrCreateFragment(ServiceHistoryCustomerFragment.class, TAG_HISTORY_CUSTOMER);
        categoryFragment = getOrCreateFragment(CategoryFragment.class, TAG_CATEGORY);
        serviceFragment = getOrCreateFragment(ServiceFragment.class, TAG_SERVICE);
    }

    private <T extends Fragment> T getOrCreateFragment(Class<T> fragmentClass, String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragmentClass.cast(fragment);
    }

    public void setupBottomNavigation(BottomNavigationView bottomNavigationView, String currentRole) {
        if (currentRole.equals(SystemRoles.MANAGER.getName())) {
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.nav_menu_manager);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.category) {
                    switchFragment(categoryFragment, TAG_CATEGORY);
                } else if (id == R.id.account) {
                    switchFragment(accountFragment, TAG_ACCOUNT);
                } else if (id == R.id.service) {
                    switchFragment(serviceFragment, TAG_SERVICE);
                } else if (id == R.id.profile) {
                    switchFragment(employeeProfileFragment, TAG_PROFILE);
                }
                return true;
            });
        } else if (currentRole.equals(SystemRoles.RECEPTIONIST.getName())) {
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.nav_menu_receptionist);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.order) {
                    switchFragment(homeFragment, TAG_HOME);
                } else if (id == R.id.room) {
                    switchFragment(roomFragment, TAG_ROOM);
                } else if (id == R.id.history) {
                    switchFragment(serviceHistoryFragment, TAG_HISTORY);
                } else if (id == R.id.profile) {
                    switchFragment(employeeProfileFragment, TAG_PROFILE);
                }
                return true;
            });
        } else if (currentRole.equals(SystemRoles.CUSTOMER.getName())) {
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.nav_menu_customer);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.service) {
                    switchFragment(homeFragmentCustomer, TAG_HOME_CUSTOMER);
                } else if (id == R.id.history) {
                    switchFragment(serviceHistoryCustomerFragment, TAG_HISTORY_CUSTOMER);
                }
                return true;
            });
        }
    }

    private void switchFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
        if (existingFragment != null && existingFragment != fragment) {
            transaction.remove(existingFragment);
       }
        if (!fragment.isAdded()) {
            transaction.add(R.id.bottom_navigation_container, fragment, tag);
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.show(fragment).commitAllowingStateLoss();
        currentFragment = fragment;
    }

    public void setInitialFragment(String currentRole, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (currentRole.equals(SystemRoles.MANAGER.getName())) {
                switchFragment(categoryFragment, TAG_CATEGORY);
            } else if (currentRole.equals(SystemRoles.RECEPTIONIST.getName())) {
                switchFragment(homeFragment, TAG_HOME);
            } else {
                switchFragment(homeFragmentCustomer, TAG_HOME_CUSTOMER);
            }
        }
    }
}

