package com.example.hsb.ui.home_customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_customer, container, false);

        tabLayout = view.findViewById(R.id.tl_categories);
        recyclerView = view.findViewById(R.id.rv_service_list);
        accountName = view.findViewById(R.id.tv_device_number);
        roomNumber = view.findViewById(R.id.tv_room_number);

        accountName.setText("sus");
        roomNumber.setText("420");
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
}