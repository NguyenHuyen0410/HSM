package com.example.hsb.ui.home_customer.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.record.RoomRecord;
import com.example.hsb.repository.RoomRepository;
import com.example.hsb.storage.SharedPrefManager;
import com.example.hsb.ui.auth.activity.LoginActivity;
import com.example.hsb.ui.home_customer.adapter.HomeAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragmentCustomer extends Fragment {

    private final List<Category> categoryList = new ArrayList<>();
    private final List<Price> priceList = new ArrayList<>();
    private HomeAdapter adapter;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private TextView accountName;
    private TextView roomNumber;
    private Button logOutButton;
    private RoomRepository roomRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_customer, container, false);

        tabLayout = view.findViewById(R.id.tl_categories);
        recyclerView = view.findViewById(R.id.rv_service_list);
        accountName = view.findViewById(R.id.tv_device_number);
        roomNumber = view.findViewById(R.id.tv_room_number);
        AccountRecord account = SharedPrefManager.getInstance().get("account", AccountRecord.class);
        if(account.getExpand().getRole().getName().equals("CUSTOMER")){
            roomRepository = RoomRepository.getInstance();
            roomRepository.getRoomByAccountId(account.getId(), new RoomRepository.FetchRoomByAccountId() {
                @Override
                public void onSuccess(List<RoomRecord> roomRecords) {
                    for(int i = 0; i < roomRecords.size(); i++){
                        if(roomRecords.get(i).getDeviceAccountId().equals(account.getId())){
                            roomNumber.setText(roomRecords.get(i).getRoomNumber());
                        }
                    }
                }
                @Override
                public void onError(Throwable t) {
                    System.out.println("Error fetching room number: " + t.getMessage());
                }
            });
        }
        accountName.setText(account.getUsername());
        HomeFragmentViewModel categoryFragmentViewModel = new HomeFragmentViewModel();

        // Observe changes in the category list
        categoryFragmentViewModel.getListCategoryLiveData().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryList.clear();
                categoryList.addAll(categories.stream().filter(category -> category.getServiceList() != null &&
                        !category.getServiceList().isEmpty()).collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
                setupTabLayout(categoryList);
                System.out.println("Category list updated: " + categoryList.size() + " categories");
            }
        });

        categoryFragmentViewModel.getListPriceLiveDataLiveData().observe(getViewLifecycleOwner(), prices -> {
            if (prices != null) {
                priceList.clear();
                priceList.addAll(prices);
                adapter.notifyDataSetChanged();
                System.out.println("Price list updated: " + categoryList.size() + " prices");
            }
        });

        adapter = new HomeAdapter(categoryList, priceList,  requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutButton = view.findViewById(R.id.customer_logout);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked Yes button
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked No button
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void setupTabLayout(List<Category> categories) {
        tabLayout.removeAllTabs(); // Clear existing tabs
        for (Category category : categories) {//only show categories that have services
            if (!category.getServiceList().isEmpty()) {
                TabLayout.Tab tab = tabLayout.newTab().setText(category.getName());
                tabLayout.addTab(tab);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                recyclerView.scrollToPosition(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance().clear();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}