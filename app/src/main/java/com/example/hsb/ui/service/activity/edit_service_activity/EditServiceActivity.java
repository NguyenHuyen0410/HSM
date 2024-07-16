package com.example.hsb.ui.service.activity.edit_service_activity;

import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.ui.service.adapter.CategoryAdapter;
import com.example.hsb.utils.DateUtil;
import com.example.hsb.utils.ValidateUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EditServiceActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText startTime;
    private EditText endTime;
    private EditText remark;
    private EditText edtPrice;
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;

    private List<Category> categoryList = new ArrayList<>();
    private List<Category> selectedCategories = new ArrayList<>();
    private EditServiceActivityViewModel editServiceActivityViewModel;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_add);
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

        // Initialize UI elements
        name = findViewById(R.id.et_name_service);
        description = findViewById(R.id.et_description_service);
        startTime = findViewById(R.id.et_start_service);
        endTime = findViewById(R.id.et_end_service);
        remark = findViewById(R.id.et_room_remark);
        Button deleteBtn = findViewById(R.id.btn_delete_room);
        Button saveBtn = findViewById(R.id.btn_save_room);
        rvCategories = findViewById(R.id.rv_categories);
        edtPrice = findViewById(R.id.et_price_service);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ViewModel
        editServiceActivityViewModel = new EditServiceActivityViewModel();

        // Observe categories and initialize adapter
        editServiceActivityViewModel.getListCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    categoryList.clear();
                    categoryList.addAll(categories);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Get the service passed to the activity
        Service service = (Service) getIntent().getSerializableExtra("service");
        Price price = (Price) getIntent().getSerializableExtra("price");
        if (service != null) {
            setData(service, price);
        } else {
            service = new Service();
        }

        RecyclerView recyclerView = findViewById(R.id.rv_categories);
        adapter = new CategoryAdapter(categoryList, selectedCategories, service.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Service finalService = service;
        saveBtn.setOnClickListener(v -> setUpdateData(finalService, price));

        deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(EditServiceActivity.this)
                .setTitle("Delete Service")
                .setMessage("Are you sure you want to delete this service?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Call ViewModel to delete service
                    finalService.setDeleted(true);
                    editServiceActivityViewModel.editService(finalService);
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        // Observe ViewModel for delete status
        editServiceActivityViewModel.getDeleteStatusLiveData().observe(this, isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Toast.makeText(EditServiceActivity.this, "Service deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Observe ViewModel for service updates
        editServiceActivityViewModel.getServiceLiveData().observe(this, updatedService -> {
            Toast.makeText(EditServiceActivity.this, "Service updated successfully", Toast.LENGTH_SHORT).show();
            finish();
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

    private void setUpdateData(@NotNull Service service, @NotNull Price price) {
        boolean isValid = true;

        // Validate name
        if (ValidateUtil.isNameValid(name)) {
            service.setName(name.getText().toString());
        } else {
            name.setError("Invalid name");
            isValid = false;
        }

        // Validate description
        if (!description.getText().toString().isEmpty()) {
            service.setDescription(description.getText().toString());
        } else {
            isValid = false;
        }

        // Validate price
        Double tempPrice = Double.valueOf(edtPrice.getText().toString());
        if (tempPrice >= 0) {
            if (!price.getPrice().equals(tempPrice)) {
                price.setDeleted(true);
                Price newPrice = new Price();
                newPrice.setPrice(tempPrice);
                newPrice.setRemark("");
                newPrice.setDeleted(false);
                newPrice.setServiceId(service.getId());
                editServiceActivityViewModel.editPrice(price);
                editServiceActivityViewModel.createPrice(newPrice);
            }
        } else {
            edtPrice.setError("Price must be >= 0");
            isValid = false;
        }

        // Validate and set start time
//        if (!startTime.getText().toString().isEmpty()) {
//            service.setStartTime(DateUtil.stringToLocalDateTime(startTime.getText().toString()));
//        } else {
//            startTime.setError("Invalid start time");
//            isValid = false;
//        }

        // Validate and set end time
//        if (!endTime.getText().toString().isEmpty()) {
//            service.setEndTime(DateUtil.stringToLocalDateTime(endTime.getText().toString()));
//        } else {
//            endTime.setError("Invalid end time");
//            isValid = false;
//        }

        // Update service if valid
        if (isValid) {
            if (service.getId() != null) {
                editServiceActivityViewModel.editService(service);
            } else {
                editServiceActivityViewModel.createService(service);
            }
        } else {
            Toast.makeText(EditServiceActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(@NotNull Service service, @NotNull Price price) {
        name.setText(service.getName());
        description.setText(service.getDescription());

        // Set start and end time
        startTime.setText(DateUtil.localDateTimeToString(service.getStartTime()));
        endTime.setText(DateUtil.localDateTimeToString(service.getEndTime()));

        // Set remark and price
        remark.setText(service.getRemark());
        edtPrice.setText(String.valueOf(price.getPrice()));

        // Notify adapter to update the selected state
        if (categoryAdapter != null) {
            categoryAdapter.notifyDataSetChanged();
        }
    }
}