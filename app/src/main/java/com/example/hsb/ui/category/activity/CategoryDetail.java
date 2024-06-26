package com.example.hsb.ui.category.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Service;
import com.example.hsb.ui.category.activity.edit_category_activity.EditCategoryActivity;
import com.example.hsb.ui.category.adapter.CategoryDetailServiceAdaptor;
//import com.example.hsb.ui.category.activity.edit_category_activity.EditCategoryActivity;
//import com.example.hsb.ui.category.adapter.CategoryDetailServiceAdaptor;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetail extends AppCompatActivity {
    private TextView name;
    private TextView description;
    private ImageView imageView;
    private Button createBtn;
    private Button updateBtn;

    private Category category;
    private List<Service> serviceList = new ArrayList<>();
    private CategoryDetailServiceAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set navigation icon (arrow) to be white
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.arrow_white);
        if (upArrow != null) {
            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

//        name = findViewById(R.id.tv_category_name);
//        description = findViewById(R.id.tv_category_description);
//        imageView = findViewById(R.id.imv_category);
//        createBtn = findViewById(R.id.btn_create_category);
//        updateBtn = findViewById(R.id.btn_update_category);

        // Get the category passed to the activity
        category = (Category) getIntent().getSerializableExtra("category");

        name.setText(category.getName());
        description.setText(category.getDescription());
        StringBuilder imgAddess = new StringBuilder();
        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/category/");
        imgAddess.append(category.getId());
        imgAddess.append("/");
        imgAddess.append(category.getImage());
        Glide.with(this).load(imgAddess.toString()).into(imageView);

//        if (category.getServiceList() != null) {
//            serviceList.clear();
//            serviceList.addAll(category.getServiceList());
//        }

        RecyclerView recyclerView = findViewById(R.id.rv_service_child);
        adapter = new CategoryDetailServiceAdaptor(serviceList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        createBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryDetail.this, EditCategoryActivity.class);
            startActivity(intent);
        });

        updateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryDetail.this, EditCategoryActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back to previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
