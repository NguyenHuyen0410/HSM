package com.example.hsb.ui.category.activity.edit_category_activity;

import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.utils.ValidateUtil;

public class EditCategoryActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private EditCategoryActivityViewModel editCategoryActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

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

        name = findViewById(R.id.et_name_category);
        description = findViewById(R.id.et_description_category);
        Button saveBtn = findViewById(R.id.btn_save);
        Button deleteBtn = findViewById(R.id.btn_delete);

        // Initialize ViewModel
        editCategoryActivityViewModel = new ViewModelProvider(this).get(EditCategoryActivityViewModel.class);

        // Get the category passed to the activity
        Category category = (Category) getIntent().getSerializableExtra("category");
        if (category != null) {
            name.setText(category.getName());
            description.setText(category.getDescription());
        } else {
            category = new Category();
        }

        Category finalCategory = category;
        saveBtn.setOnClickListener(v -> setUpdateData(finalCategory));

        deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(EditCategoryActivity.this)
                .setTitle("Delete Category")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Call ViewModel to delete category
                    editCategoryActivityViewModel.deleteCategory(finalCategory.getId());
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        // Observe the ViewModel for category updates
        editCategoryActivityViewModel.getDeleteStatusLiveData().observe(this, isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Toast.makeText(EditCategoryActivity.this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        editCategoryActivityViewModel.getCategoryLiveData().observe(this, updatedCategory -> {
            // Handle the updated account, e.g., show a message or update UI
            Toast.makeText(EditCategoryActivity.this, "Category updated successfully", Toast.LENGTH_SHORT).show();
            // Optionally finish the activity or update the UI further
            finish();
        });

        // Observe category list updates to ensure the latest data is displayed
        editCategoryActivityViewModel.getCategoryListLiveData().observe(this, categories -> {
            // Handle the updated list of categories if needed
            // This can be useful if you have a dropdown or list that should be updated
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

    public void setUpdateData(@Nullable Category category) {
        boolean isValid = true;

        // Validate name
        if (ValidateUtil.isNameValid(name)) {
            category.setName(name.getText().toString());
        } else {
            name.setError("Invalid name");
            isValid = false;
        }

        if (isValid) {
            // Call ViewModel to update or create category
            if (category.getId() != null) {
                editCategoryActivityViewModel.editCategory(category);
            } else {
                editCategoryActivityViewModel.createCategory(category);
            }
        } else {
            Toast.makeText(EditCategoryActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }
}