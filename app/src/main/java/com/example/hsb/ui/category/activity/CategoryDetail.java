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
import com.example.hsb.ui.category.adapter.ServiceAdaptor;
import com.example.hsb.ui.category.fragment.ServiceFragmentViewModel;

import java.util.List;

public class CategoryDetail extends AppCompatActivity {
    private TextView name;
    private TextView description;
    private ImageView imageView;
    private Button createBtn;
    private Button updateBtn;
    private Category category;
    private List<Service> serviceList;
    private ServiceAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set navigation icon (arrow) to be white
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24);
        if (upArrow != null) {
            upArrow.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        name = findViewById(R.id.tv_name_category);
        description = findViewById(R.id.tv_description_category);
        imageView = findViewById(R.id.iv_image_category);
        updateBtn = findViewById(R.id.btn_update_category);
        createBtn = findViewById(R.id.btn_create_category);

        // Get the category passed to the activity
        category = (Category) getIntent().getSerializableExtra("category");
        serviceList = category.getServiceList();


        name.setText(category.getName());
        description.setText(category.getDescription());
        StringBuilder imgAddress = new StringBuilder();
        imgAddress.append("https://hotel-service-manage.pockethost.io/api/files/category/");
        imgAddress.append(category.getId());
        imgAddress.append("/");
        imgAddress.append(category.getImage());
        Glide.with(this).load(imgAddress.toString()).into(imageView);

//        serviceFragmentViewModel = new ServiceFragmentViewModel();
//        serviceFragmentViewModel.getListServiceLiveData().observe(this, new Observer<List<Service>>() {
//            @Override
//            public void onChanged(List<Service> services) {
//                if (services != null) {
//                    serviceList.clear();
//                    serviceList.addAll(services);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });
//
//        serviceFragmentViewModel.getToastMessageLiveData().observe(this, message -> {
//            if (message != null) {
//                Toast.makeText(CategoryDetail.this, message, Toast.LENGTH_SHORT).show();
//            }
//        });

        RecyclerView recyclerView = findViewById(R.id.rcv_category_service);
        adapter = new ServiceAdaptor(serviceList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryDetail.this, EditCategoryActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });
        createBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryDetail.this, EditCategoryActivity.class);
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
