package com.example.hsb.ui.account.fragment.category_fragment;

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
import com.example.hsb.entities.Service;
import com.example.hsb.ui.account.activity.edit_category_activity.EditCategoryActivity;
import com.example.hsb.ui.account.adapter.CategoryAdaptor;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment  extends Fragment {
    private List<Category> categoryList = new ArrayList<>();
    private CategoryAdaptor adapter;
    private CategoryFragmentViewModel categoryFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

//        categoryFragmentViewModel = new CategoryFragmentViewModel();
//
//        // Observe changes in the account list
//        categoryFragmentViewModel.getListCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
//            @Override
//            public void onChanged(List<Category> categories) {
//                if (categories != null) {
//                    categoryList.clear();
//                    categoryList.addAll(categories);
//                    adapter.notifyDataSetChanged();
//                    System.out.println("Category list updated: " + categoryList.size() + " catogories");
//                }
//            }
//        });
        categoryList = getCategoryList();
        RecyclerView recyclerView = view.findViewById(R.id.category_list);
        adapter = new CategoryAdaptor(categoryList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return view;

    }

    public List<Category> getCategoryList() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(
                "1",
                "Sự kiện",
                "1.png",
                "des 1",
                false,
                null,
                null,
                ChildItemList())
        );
        list.add(new Category(
                "2",
                "Dịch vụ ăn uống",
                "2.png",
                "des 2",
                false,
                null,
                null,
                ChildItemList())
        );
        list.add(new Category(
                "3",
                "Giải trí và thư giãn",
                "3.png",
                "des 3",
                false,
                null,
                null,
                ChildItemList())
        );

        list.add(new Category(
                "6",
                "Sự kiện",
                "4.png",
                "des 3",
                false,
                null,
                null,
                ChildItemList())
        );
        return list;
    }

    private List<Service> ChildItemList() {
        List<Service> ChildItemList = new ArrayList<>();
        ChildItemList.add(new Service("1", "test 1", "test image", "test des", null, null, "test", null, null, false));
        ChildItemList.add(new Service("2", "test 2", "test image", "test des", null, null, "test", null, null, false));
        ChildItemList.add(new Service("3", "test 3", "test image", "test des", null, null, "test", null, null, false));
        ChildItemList.add(new Service("4", "test 4 ", "test image", "test des", null, null, "test", null, null, false));
        ChildItemList.add(new Service("4", "test 4 ", "test image", "test des", null, null, "test", null, null, false));
        ChildItemList.add(new Service("4", "test 4 ", "test image", "test des", null, null, "test", null, null, false));
        return ChildItemList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addAccountBtn = view.findViewById(R.id.btn_add_category);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditCategoryActivity.class);
                startActivity(i);
            }
        });
    }
}