package com.example.hsb.ui.category.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.ui.service.adapter.ServiceAdaptor;
import com.example.hsb.ui.category.activity.edit_category_activity.CategoryDetail;


import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.CategoryHolder> {

    private List<Category> categoryList;
    private Context context;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public CategoryAdaptor(List<Category> categoryList, Context context) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        StringBuilder imgAddess = new StringBuilder();


        Category category = categoryList.get(position);
        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/category/");
        imgAddess.append(category.getId());
        imgAddess.append("/");
        imgAddess.append(category.getImage());
        Glide.with(context).load(imgAddess.toString()).into(holder.images);

        holder.name.setText(category.getName());

        holder.images.setImageResource(R.drawable.hotel_logo);

            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.
                    childRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        if (category.getServiceList() != null) {
            layoutManager.setInitialPrefetchItemCount(category.getServiceList().size());

            ServiceAdaptor childItemAdapter = new ServiceAdaptor(category.getServiceList());
            holder.childRecyclerView.setLayoutManager(layoutManager);
            holder.childRecyclerView.setAdapter(childItemAdapter);
            holder.childRecyclerView.setRecycledViewPool(viewPool);
        }


        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(context, CategoryDetail.class);
            intent.putExtra("category", category);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView images;
        Button button;

        private RecyclerView childRecyclerView;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_category_name);
            images = itemView.findViewById(R.id.imv_category);
            button = itemView.findViewById(R.id.btn_category_edit);
            childRecyclerView = itemView.findViewById(R.id.rv_service_child);
        }
    }
}