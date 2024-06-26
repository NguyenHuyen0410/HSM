package com.example.hsb.ui.category.fragment.category_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.ui.category.adapter.CategoryAdaptor;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private List<Category> categoryList = new ArrayList<>();
    private CategoryAdaptor adapter;
    private CategoryFragmentViewModel categoryFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryFragmentViewModel = new CategoryFragmentViewModel();

        // Observe changes in the account list
        categoryFragmentViewModel.getListCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    categoryList.clear();
                    categoryList.addAll(categories);
                    adapter.notifyDataSetChanged();
                    System.out.println("Category list updated: " + categoryList.size() + " catogories");
                }
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.category_list);
        adapter = new CategoryAdaptor(categoryList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return view;

    }

}