package com.example.hsb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.R;

public class CreateServiceActivity extends AppCompatActivity {
    String[] categories = {"Category 1", "Category 2", "Category 3"};

    AutoCompleteTextView categoryInput;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        categoryInput = findViewById(R.id.auto_complete_text);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_service_category, categories);

        categoryInput.setAdapter(adapterItems);
        categoryInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateServiceActivity.this, item, Toast.LENGTH_SHORT).show();

            }
        });
    }
}