package com.example.hsb.ui.service.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Service;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<Category> categoryList;
    private final List<Category> selectedCategoryList;

    private final String serviceId;

    public CategoryAdapter(List<Category> categoryList, List<Category> selectedCategoryList, String id) {
        this.categoryList = categoryList;
        this.selectedCategoryList = selectedCategoryList;
        this.serviceId = id;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_checkbox, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getName());
        holder.categoryCheckBox.setChecked(false);
        for (Service service1 : category.getServiceList()) {
            if (service1.getId().equals(serviceId)) {
                holder.categoryCheckBox.setChecked(true);
                selectedCategoryList.add(category);
            }
        }

        holder.categoryCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedCategoryList.add(category);
            } else {
                selectedCategoryList.remove(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CheckBox categoryCheckBox;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tv_category_name);
            categoryCheckBox = itemView.findViewById(R.id.cb_category);
        }
    }
}