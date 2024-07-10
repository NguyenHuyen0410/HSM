package com.example.hsb.ui.customer_history.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.ui.category.fragment.ServiceFragmentViewModel;

public class OrderedServiceDetail extends AppCompatActivity {
    private TextView serviceBillDetailId;
    private TextView processedDate;
    private TextView processedTime;
    private TextView status;
    private TextView name;
    private TextView priceValue;

    private TextView quantity;

    private TextView totalCost;

    private TextView remark;

    private ImageView image;


    private ServiceFragmentViewModel serviceFragmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_request_history_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set navigation icon (arrow) to be white
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.arrow_white);
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

        Service service = (Service) getIntent().getSerializableExtra("service");
        ServiceBillDetail serviceBillDetail = (ServiceBillDetail) getIntent().getSerializableExtra("serviceBillDetail");
        setData(service, serviceBillDetail);

    }

    private void setData(Service service, ServiceBillDetail serviceBillDetail) {
        String date;
        String time ;
        if (serviceBillDetail.getProcessDate() != null) {
            date = serviceBillDetail.getProcessDate().getDayOfMonth () +"/" + serviceBillDetail.getProcessDate().getMonthValue();
            time = serviceBillDetail.getProcessDate().getHour () +":" + serviceBillDetail.getProcessDate().getMinute();
        } else {
            date = "Not ready";
            time = "Not ready";
        }

        Price price = serviceBillDetail.getPrice();
        Double finalCost = price.getPrice() * serviceBillDetail.getQuantity();
        StringBuilder imgAddess = new StringBuilder();
        serviceBillDetailId.setText(serviceBillDetail.getId());
        processedDate.setText(date);
        processedTime.setText(time);
        status.setText(serviceBillDetail.getStatus());
        
        name.setText(service.getName());
        priceValue.setText(String.valueOf(price.getPrice())); // Display price as string
        quantity.setText(String.valueOf(serviceBillDetail.getQuantity()));
        totalCost.setText(String.valueOf(finalCost));
        remark.setText(serviceBillDetail.getRemark());


        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/services/");
        imgAddess.append(service.getId());
        imgAddess.append("/");
        imgAddess.append(service.getImage());
        Glide.with(this).load(imgAddess.toString()).into(image  );

    }

    private void setLayout() {
        serviceBillDetailId = findViewById(R.id.tv_order_id);
        processedDate = findViewById(R.id.tv_processed_date);
        processedTime = findViewById(R.id.tv_processed_time);
        status = findViewById(R.id.tv_status);
        name = findViewById(R.id.tv_service_name);
        priceValue = findViewById(R.id.tv_service_price);
        quantity = findViewById(R.id.tv_service_quantity);
        totalCost = findViewById(R.id.tv_service_total_cost);
        remark = findViewById(R.id.tv_remark);
        image = findViewById(R.id.iv_service);
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
