package com.example.hsb.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.ui.account.activity.edit_account_activity.EditAccountActivity;
import com.example.hsb.ui.home.adapter.HomeAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private List<Category> categoryList = new ArrayList<>();
    private List<Price> priceList = new ArrayList<>();
    private HomeAdapter adapter;
    private HomeFragmentViewModel categoryFragmentViewModel;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.table1);
        recyclerView = view.findViewById(R.id.rv_service_list);

        categoryFragmentViewModel = new HomeFragmentViewModel();

        // Observe changes in the category list
        categoryFragmentViewModel.getListCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                if (categories != null) {
                    categoryList.clear();
                    categoryList.addAll(categories.stream().filter(category -> category.getServiceList() != null &&
                            !category.getServiceList().isEmpty()).collect(Collectors.toList()));
                    adapter.notifyDataSetChanged();
                    setupTabLayout(categoryList);
                    System.out.println("Category list updated: " + categoryList.size() + " categories");
                }
            }
        });

        categoryFragmentViewModel.getListPriceLiveDataLiveData().observe(getViewLifecycleOwner(), new Observer<List<Price>>() {
            @Override
            public void onChanged(@Nullable List<Price> prices) {
                if (prices != null) {
                    priceList.clear();
                    priceList.addAll(prices);
                    adapter.notifyDataSetChanged();
                    System.out.println("Price list updated: " + categoryList.size() + " prices");
                }
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