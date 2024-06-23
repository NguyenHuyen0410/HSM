package com.example.hsb.ui.account.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Category;
import com.example.hsb.storage.AccountStatus;
import com.example.hsb.ui.account.activity.edit_account_activity.EditAccountActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.CategoryHolder> {

    private List<Category> categoryList;
    private Context context;
    private static String hexColor;

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
        Category category = this.categoryList.get(position);
        Glide.with(context)
                .load(category.getImage()) // replace with your image source
                .apply(RequestOptions.circleCropTransform())
                .into(holder.images);

        holder.name.setText(category.getName());

        holder.images.setImageResource(R.drawable.android_image_1);

//        holder.button.setOnClickListener(v -> {
//            Intent intent = new Intent(context, EditCategoryActivity.class);
//            intent.putExtra("category", category);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        TextView name;
//        TextView servies; lIST OUT SERVICES
        ImageView images;
        Button button;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_category_name);
            images = itemView.findViewById(R.id.imv_category);
            button = itemView.findViewById(R.id.button);
        }
    }

    public static String localDateTimeToString(LocalDateTime input) {
        // Define the output format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Format the LocalDateTime object to the desired format
        return input.format(formatter);
    }
}