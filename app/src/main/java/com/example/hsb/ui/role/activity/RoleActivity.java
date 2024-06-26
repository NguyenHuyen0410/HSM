package com.example.hsb.ui.role.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hsb.R;
import com.example.hsb.entities.Role;
import com.example.hsb.ui.role.viewmodel.RoleViewModel;

import java.util.List;

public class RoleActivity extends AppCompatActivity {
    private RoleViewModel roleViewModel;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category);

//        listView = findViewById(R.id.listView);

//        roleViewModel = new ViewModelProvider(this).get(RoleViewModel.class);
//        roleViewModel.getRoles().observe(this, new Observer<List<Role>>() {
//            @Override
//            public void onChanged(List<Role> roles) {
//                ArrayAdapter<Role> adapter = new ArrayAdapter<>(RoleActivity.this, android.R.layout.simple_list_item_1, roles);
//                listView.setAdapter(adapter);
//            }
//        });
//    }
    }
}
