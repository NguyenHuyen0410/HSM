package com.example.hsb.ui.home_customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CategoryHolder> {

    private final List<Category> categoryList;
    private final List<Price> priceList;
    private final Context context;

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public HomeAdapter(List<Category> categoryList, List<Price> priceList, Context context) {
        this.context = context;
        this.categoryList = categoryList;
        this.priceList = priceList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.name.setText(category.getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.childRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        if (category.getServiceList() != null) {
            layoutManager.setInitialPrefetchItemCount(category.getServiceList().size());

            ServiceAdaptor childItemAdapter = new ServiceAdaptor(category.getServiceList(), priceList, context);
            holder.childRecyclerView.setLayoutManager(layoutManager);
            holder.childRecyclerView.setAdapter(childItemAdapter);
            holder.childRecyclerView.setRecycledViewPool(viewPool);
            holder.childRecyclerView.setNestedScrollingEnabled(false); // Disable scrolling for childRecyclerView
        }

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        TextView name;

        private final RecyclerView childRecyclerView;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_home_category_name);
            childRecyclerView = itemView.findViewById(R.id.rv_service_child);
        }
    }
}