package com.example.hsb.ui.home_customer.activity.edit_order_service_activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;

public class OrderServiceCustomerActivity extends AppCompatActivity {
    private TextView name;
    private TextView tvPrice;
    private TextView tvAmount;

    private ImageView imageView;

    private int totalAmount = 0;
    private EditText remark;
    private Button addButton;
    private Button removeButton;
    private Button orderButton;
    private OrderServiceActivityViewModel orderServiceActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_customer);
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

        setLayout();

        // Initialize ViewModel
        orderServiceActivityViewModel = new ViewModelProvider(this).get(OrderServiceActivityViewModel.class);

        // Get the order passed to the activity
        Service service = (Service) getIntent().getSerializableExtra("service");
        Price price = (Price) getIntent().getSerializableExtra("price");
        setData(service, price);

        // Set up role AutoCompleteTextView


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmount++;
                updateAmountAndPrice(price);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalAmount > 0) {
                    totalAmount--;
                    updateAmountAndPrice(price);
                }
            }
        });


        // Observe the ViewModel for toast messages
        orderServiceActivityViewModel.getToastMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(OrderServiceCustomerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateData(service,price);
            }
        });


        // Observe the ViewModel for toast messages
//        orderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle order logic here
//                Toast.makeText(OrderServiceActivity.this, "Order placed!", Toast.LENGTH_SHORT).show();
//            }
//        });

        // Observe the ViewModel for toast messages
//        orderServiceActivityViewModel.getToastMessageLiveData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String message) {
//                Toast.makeText(OrderServiceActivity.this, message, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void setLayout() {
        name = findViewById(R.id.tv_name_service);
        tvPrice = findViewById(R.id.tv_service_price);
        tvAmount = findViewById(R.id.tv_service_amount);
        remark = findViewById(R.id.et_remark);
        addButton = findViewById(R.id.btn_add);
        removeButton = findViewById(R.id.btn_remove);
        orderButton = findViewById(R.id.btn_order);
        imageView = findViewById(R.id.iv_image_service);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back to previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpdateData(@Nullable Service service,@Nullable Price price) {
        boolean isValid = true;
        ServiceBillDetail serviceBillDetail = new ServiceBillDetail(
                null,
                service.getId(),
                totalAmount,
                "waiting",
                remark.getText().toString(),
                "cg5l5zzyi7trfdb",
                price.getId(),
                null,
                false,
                null,
                null,
                price
        );
        // Validate name
        if (totalAmount > 0) {
            serviceBillDetail.setQuantity(totalAmount);
        } else {
            tvAmount.setError("Hay dat nhieu hon 0");
            isValid = false;
        }

        if (isValid) {

            // Call ViewModel to update or create category
            orderServiceActivityViewModel.createServiceBillDetail(serviceBillDetail);
            Toast.makeText(OrderServiceCustomerActivity.this, "Order placed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(OrderServiceCustomerActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(@Nullable Service service, Price priceRecord) {
        StringBuilder imgAddess = new StringBuilder();
        name.setText(service.getName());
        tvPrice.setText(String.valueOf(priceRecord.getPrice())); // Display price as string
        tvAmount.setText(String.valueOf(totalAmount));
        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/services/");
        imgAddess.append(service.getId());
        imgAddess.append("/");
        imgAddess.append(service.getImage());
        Glide.with(this).load(imgAddess.toString()).into(imageView);
    }

    private void updateAmountAndPrice(Price price) {
        double totalPrice = price.getPrice() * totalAmount;
        tvAmount.setText(String.valueOf(totalAmount));
        tvPrice.setText(String.valueOf(totalPrice)); // Display total price
    }

}