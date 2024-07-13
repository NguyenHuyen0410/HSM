package com.example.hsb.ui.history.activity;

import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.storage.ServiceBillDetailStatus;
import com.example.hsb.storage.SystemRoles;
import com.example.hsb.ui.account.activity.edit_account_activity.EditAccountActivity;
import com.example.hsb.ui.category.fragment.ServiceFragmentViewModel;
import com.example.hsb.ui.home.activity.edit_order_service_activity.OrderServiceActivity;

public class EditOrderedServiceDetail extends AppCompatActivity {
    private final String[] statusItems = {"waiting", "preparing", "doing", "done", "canceled"};
    private TextView serviceBillDetailId;
    private TextView processedDate;
    private TextView processedTime;
    private AutoCompleteTextView autoCompleteStatus;
    private TextView name;
    private TextView priceValue;

    private TextView quantity;

    private TextView totalCost;

    private TextView remark;

    private ImageView image;

    private Button saveBtn;

    private Button deleteBtn;

    private EditOrderedServiceDetailViewModel editOrderedServiceDetailViewModel;
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

        editOrderedServiceDetailViewModel = new EditOrderedServiceDetailViewModel();
        setLayout();



        Service service = (Service) getIntent().getSerializableExtra("service");
        ServiceBillDetail serviceBillDetail = (ServiceBillDetail) getIntent().getSerializableExtra("serviceBillDetail");
        setData(service, serviceBillDetail);

        saveBtn.setOnClickListener(v -> setUpdateData(serviceBillDetail));

        deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(EditOrderedServiceDetail.this)
                .setTitle("Delete bill")
                .setMessage("Are you sure you want to delete this bill?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Call ViewModel to delete account
                    editOrderedServiceDetailViewModel.deleteServiceBillDetail(serviceBillDetail.getId());
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
        // Set up status AutoCompleteTextView
        ArrayAdapter<String> arrayStatusAdapter = new ArrayAdapter<>(this, R.layout.list_status_item, statusItems);
        autoCompleteStatus.setAdapter(arrayStatusAdapter);

        autoCompleteStatus.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            Toast.makeText(EditOrderedServiceDetail.this, selectedItem, Toast.LENGTH_SHORT).show();
        });

        // Observe the ViewModel for delete status
        editOrderedServiceDetailViewModel.getDeleteStatusLiveData().observe(this, isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Toast.makeText(EditOrderedServiceDetail.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Observe the ViewModel for account updates
        editOrderedServiceDetailViewModel.getMServiceBillDetail().observe(this, updatedAccount -> {
            // Handle the updated account, e.g., show a message or update UI
            Toast.makeText(EditOrderedServiceDetail.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
            // Optionally finish the activity or update the UI further
            finish();
        });

        // Observe the ViewModel for toast messages
        editOrderedServiceDetailViewModel.getToastMessageLiveData().observe(this, message -> Toast.makeText(EditOrderedServiceDetail.this, message, Toast.LENGTH_SHORT).show());
    }

    private void setData(Service service, ServiceBillDetail serviceBillDetail) {
        String date;
        String time;
        if (serviceBillDetail.getProcessDate() != null) {
            date = serviceBillDetail.getProcessDate().getDayOfMonth() + "/" + serviceBillDetail.getProcessDate().getMonthValue();
            time = serviceBillDetail.getProcessDate().getHour() + ":" + serviceBillDetail.getProcessDate().getMinute();
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
        autoCompleteStatus.setText(serviceBillDetail.getStatus(), false);

        name.setText(service.getName());
        priceValue.setText(String.valueOf(price.getPrice())); // Display price as string
        quantity.setText(String.valueOf(serviceBillDetail.getQuantity()));
        totalCost.setText(String.valueOf(finalCost));
        remark.setText(serviceBillDetail.getRemark());


        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/services/");
        imgAddess.append(service.getId());
        imgAddess.append("/");
        imgAddess.append(service.getImage());
        Glide.with(this).load(imgAddess.toString()).into(image);

    }

    public void setUpdateData(@Nullable ServiceBillDetail serviceBillDetail) {
        boolean isValid = true;

        // Validate name
        String updatedStatus = autoCompleteStatus.getText().toString();
        switch (updatedStatus) {
            case "waiting":
                serviceBillDetail.setStatus(ServiceBillDetailStatus.WAITING);
                break;
            case "preparing":
                serviceBillDetail.setStatus(ServiceBillDetailStatus.PREPARING);
                break;
            case "doing":
                serviceBillDetail.setStatus(ServiceBillDetailStatus.DOING);
                break;
            case "done":
                serviceBillDetail.setStatus(ServiceBillDetailStatus.DONE);
                break;
            case "canceled":
                serviceBillDetail.setStatus(ServiceBillDetailStatus.CANCELED);
                break;
            default:
                autoCompleteStatus.setError("Invalid status");
                isValid = false;
                break;
        }

        if (isValid) {

            // Call ViewModel to update or create category
            editOrderedServiceDetailViewModel.editServiceBillDetail(serviceBillDetail); ;
            Toast.makeText(EditOrderedServiceDetail.this, "Bill updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditOrderedServiceDetail.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLayout() {
        serviceBillDetailId = findViewById(R.id.tv_order_id);
        processedDate = findViewById(R.id.tv_processed_date);
        processedTime = findViewById(R.id.tv_processed_time);
        autoCompleteStatus = findViewById(R.id.auto_complete_status);
        name = findViewById(R.id.tv_service_name);
        priceValue = findViewById(R.id.tv_service_price);
        quantity = findViewById(R.id.tv_service_quantity);
        totalCost = findViewById(R.id.tv_service_total_cost);
        remark = findViewById(R.id.tv_remark);
        image = findViewById(R.id.iv_service);
        saveBtn = findViewById(R.id.btn_save);
        deleteBtn = findViewById(R.id.btn_delete);
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
