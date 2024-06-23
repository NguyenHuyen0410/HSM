//package com.example.hsb.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.hsb.R;
//import com.example.hsb.entities.Service;
//import com.example.hsb.ui.adapter.ServiceAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ViewServiceActivity extends AppCompatActivity {
//    private Button createService;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_service);
//        List<Service> services = new ArrayList<>();
//        services.add(new Service("Service 1", R.drawable.android_image_1, "Description 1"));
//        services.add(new Service("Service 2", R.drawable.android_image_2, "Description 2"));
//        services.add(new Service("Service 3", R.drawable.android_image_3, "Description 3"));
//        services.add(new Service("Service 4", R.drawable.android_image_4, "Description 4"));
//        services.add(new Service("Service 5", R.drawable.android_image_5, "Description 5"));
//        services.add(new Service("Service 6", R.drawable.android_image_6, "Description 6"));
//        services.add(new Service("Service 7", R.drawable.android_image_7, "Description 7"));
//
//        RecyclerView recyclerView = findViewById(R.id.service_list);
//
//        ServiceAdapter adapter = new ServiceAdapter(services);
//        LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
//        linear.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linear);
//        recyclerView.setAdapter(adapter);
//
//        createService = findViewById(R.id.btn_create);
//        createService.setOnClickListener(v -> {
//            Intent intent = new Intent(ViewServiceActivity.this, CreateServiceActivity.class);
//            startActivity(intent);
//        });
//
//    }
//}