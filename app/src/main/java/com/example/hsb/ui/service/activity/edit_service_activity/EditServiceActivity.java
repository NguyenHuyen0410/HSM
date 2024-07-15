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
import com.example.hsb.entities.Service;
import androidx.lifecycle.ViewModelProvider;

import com.example.hsb.R;
import com.example.hsb.ui.category.activity.edit_category_activity.EditCategoryActivity;
import com.example.hsb.utils.DateUtil;
import com.example.hsb.utils.ValidateUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class EditServiceActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditText image;
    private EditText startTime;
    private EditText endTime;
    private EditText remark;


    private EditServiceActivityViewModel editServiceActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_add);
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
        Service service = (Service) getIntent().getSerializableExtra("service");
        name = findViewById(R.id.et_name_service);
        description = findViewById(R.id.et_description_service);
        startTime = findViewById(R.id.et_start_service);
        endTime = findViewById(R.id.et_end_service);
        remark = findViewById(R.id.et_room_remark);
        Button deleteBtn = findViewById(R.id.btn_delete_room);
        Button saveBtn = findViewById(R.id.btn_save_room);
        // Initialize ViewModel
        editServiceActivityViewModel = new ViewModelProvider(this).get(EditServiceActivityViewModel.class);
        // Get the service passed to the activity
        if (service != null) {
            setData(service);
        } else {
            service = new Service();
        }
        Service finalService = service;
        saveBtn.setOnClickListener(v -> setUpdateData(finalService));

        deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(EditServiceActivity.this)
                .setTitle("Delete Service")
                .setMessage("Are you sure you want to delete this service?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Call ViewModel to delete account
                    editServiceActivityViewModel.deleteService(finalService.getId());
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        // Observe the ViewModel for category updates
        editServiceActivityViewModel.getDeleteStatusLiveData().observe(this, isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Toast.makeText(EditServiceActivity.this, "Service deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Observe the ViewModel for service updates
        editServiceActivityViewModel.getServiceLiveData().observe(this, updatedService -> {
            // Handle the updated account, e.g., show a message or update UI
            Toast.makeText(EditServiceActivity.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
            // Optionally finish the activity or update the UI further
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

    private void setUpdateData(@NotNull Service service) {
        boolean isValid = true;
        // Validate name
        if (ValidateUtil.isNameValid(name)) {
            service.setName(name.getText().toString());
        } else {
            name.setError("Invalid name");
            isValid = false;
        }
        if (isValid) {
            // Call ViewModel to update or create category
            if (service.getId() != null) {
                editServiceActivityViewModel.editService(service);
            } else {
                editServiceActivityViewModel.editService(service);
            }
        } else {
            Toast.makeText(EditServiceActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }

        //Validate description
        if (ValidateUtil.isDescValid(description)) {
            service.setDescription(description.getText().toString());
        }else{
            isValid = false;
        }
        if (isValid) {
            // Call ViewModel to update or create category
            if (service.getId() != null) {
                editServiceActivityViewModel.editService(service);
            } else {
                editServiceActivityViewModel.editService(service);
            }
        } else {
            Toast.makeText(EditServiceActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }

        //Validate price
        
    }


    public void setData(@NotNull Service service){
        name.setText(service.getName());
        description.setText(service.getDescription());

        startTime.setText(DateUtil.localDateTimeToString(service.getStartTime()));
        endTime.setText(DateUtil.localDateTimeToString(service.getEndTime()));
        remark.setText(service.getRemark());
    }

}
