package com.example.hsb.ui.category.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.ui.category.adapter.CategoryAdaptor;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private final List<Category> categoryList = new ArrayList<>();
    private CategoryAdaptor adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        CategoryFragmentViewModel categoryFragmentViewModel = new ViewModelProvider(requireActivity()).get(CategoryFragmentViewModel.class);

        categoryFragmentViewModel.getListCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    categoryList.clear();
                    categoryList.addAll(categories);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        categoryFragmentViewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.category_list);
        adapter = new CategoryAdaptor(categoryList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}