package com.example.hsb.ui.account.activity.edit_category_activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.ui.account.activity.CategoryDetail;

public class EditCategoryActivity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private Button saveBtn;
    private Button deleteBtn;
    private EditCategoryActivityViewModel editCategoryActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_category);

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

        name = findViewById(R.id.et_name);
        description = findViewById(R.id.et_description);
        saveBtn = findViewById(R.id.btn_save);
        deleteBtn = findViewById(R.id.btn_delete);

        // Initialize ViewModel
        editCategoryActivityViewModel = new ViewModelProvider(this).get(EditCategoryActivityViewModel.class);

        // Get the account passed to the activity
        Category category = (Category) getIntent().getSerializableExtra("category");
        if (category != null) {
            name.setText(category.getName());
            description.setText(category.getDescription());
        } else {
            category = new Category();
        }

        Category finalCategory = category;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(finalCategory);
                if (finalCategory.getId() != null) {
                    // Call ViewModel to update account
                    editCategoryActivityViewModel.editCategory(finalCategory);
                } else {
                    // Call ViewModel to create account
                    editCategoryActivityViewModel.createCategory(finalCategory);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCategoryActivityViewModel.deleteCategory(finalCategory.getId());
            }
        });

        // Observe the ViewModel for account updates
        editCategoryActivityViewModel.getCategoryLiveData().observe(this, new Observer<Category>() {

            @Override
            public void onChanged(Category updatedCategory) {
                // Handle the updated account, e.g., show a message or update UI
                Toast.makeText(EditCategoryActivity.this, "Category updated successfully", Toast.LENGTH_SHORT).show();
                // Optionally finish the activity or update the UI further
//                finish();

                Intent intent = new Intent(EditCategoryActivity.this, CategoryDetail.class);
                intent.putExtra("category", editCategoryActivityViewModel.getCategoryLiveData().getValue());
                startActivity(intent);
            }
        });

        // Observe the ViewModel for toast messages
        editCategoryActivityViewModel.getToastMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(EditCategoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }
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

    public void setData(@Nullable Category category) {
        // Retrieve data from the fields
        String updatedName = name.getText().toString();
        String updatedCategory = description.getText().toString();

        // Update the account
        category.setName(updatedName);
        category.setDescription(updatedCategory);
    }
}