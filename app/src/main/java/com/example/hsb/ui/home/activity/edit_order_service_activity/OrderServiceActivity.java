package com.example.hsb.ui.home.activity.edit_order_service_activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.hsb.entities.Room;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.ui.home.fragment.HomeFragmentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderServiceActivity extends AppCompatActivity {
    private TextView name;
    private TextView tvPrice;
    private TextView tvAmount;

    private ImageView imageView;

    private int totalAmount = 0;
    private EditText remark;
    private Button addButton;
    private Button removeButton;
    private Button orderButton;

    private ArrayAdapter<Room> adapter;

    private AutoCompleteTextView autoCompleteRooms;
//    private OrderServiceActivityViewModel orderServiceActivityViewModel;

    private String selectedRoomId;

    private List<Room> roomList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        System.out.println("Badfasdfasdfdasf");

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

        setLayout();

        // Initialize ViewModel
//        orderServiceActivityViewModel = new OrderServiceActivityViewModel();

        // Get the order passed to the activity
        Service service = (Service) getIntent().getSerializableExtra("service");
        Price price = (Price) getIntent().getSerializableExtra("price");
        setData(service, price);

  

//        orderServiceActivityViewModel.getListRoomLiveData().observe(this, rooms -> {
//            if (rooms != null) {
//                roomList.clear();
//                roomList.addAll(rooms);
//                adapter.notifyDataSetChanged();
//            }
//        });


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

        adapter = new ArrayAdapter<>(this, R.layout.list_room_item, roomList);
        autoCompleteRooms.setAdapter(adapter);


        autoCompleteRooms.setOnItemClickListener((parent, view, position, id) -> {
            Room selectedRoomRecord = (Room) parent.getItemAtPosition(position);
            selectedRoomId= selectedRoomRecord.getId();
            Toast.makeText(OrderServiceActivity.this, "Selected ID: " + selectedRoomId, Toast.LENGTH_SHORT).show();
        });


        // Observe the ViewModel for toast messages
//        orderServiceActivityViewModel.getToastMessageLiveData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String message) {
//                Toast.makeText(OrderServiceActivity.this, message, Toast.LENGTH_SHORT).show();
//            }
//        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateData(service,price);
            }
        });



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
        autoCompleteRooms = findViewById(R.id.auto_complete_room);
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
                "ryh7idmam2q3k4m",
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

        if (selectedRoomId != null) {
//            orderServiceActivityViewModel.getServiceBillLiveData("room_id",selectedRoomId);

        } else {
            autoCompleteRooms.setError("Invalid room");
            isValid = false;
        }

        if (isValid) {

            // Call ViewModel to update or create category
//            orderServiceActivityViewModel.createServiceBillDetail(serviceBillDetail);
            Toast.makeText(OrderServiceActivity.this, "Order placed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(OrderServiceActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
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