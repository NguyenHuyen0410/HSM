package com.example.hsb.ui.employee.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hsb.R;
import com.example.hsb.entities.Employee;
import com.example.hsb.utils.DateUtil;
import com.example.hsb.utils.ImageLoaderUtil;
import com.example.hsb.utils.ValidateUtil;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MultipartBody;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivAccountAvt;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etDateOfBirth;
    private EditText etPhoneNumber;
    private RadioGroup rgGender;
    private EditText etAddress;
    private Button btnSave;
    private EditProfileActivityViewModel editProfileActivityViewModel;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set navigation icon (arrow) to be white
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.arrow_white);
        if (upArrow != null) {
            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        }

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setLayout();
        Employee employee = (Employee) getIntent().getSerializableExtra("profileInfo");
        if(employee!=null){
            setData(employee);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Glide.with(EditProfileActivity.this)
                    .load(selectedImageUri) // Load the new image
                    .apply(RequestOptions.circleCropTransform()) // Apply circle crop transformation
                    .into(ivAccountAvt); // Set it to your ImageView
        }
    }

    private void setLayout(){
        ivAccountAvt = findViewById(R.id.account_avt);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etDateOfBirth = findViewById(R.id.et_date_of_birth);
        etDateOfBirth.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        etDateOfBirth.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                    }, year, month, day);

            datePickerDialog.show();
        });
        etPhoneNumber = findViewById(R.id.et_phone);
        rgGender = findViewById(R.id.rgGender);
        etAddress = findViewById(R.id.et_address);
        btnSave = findViewById(R.id.btn_save);

        ivAccountAvt.setOnClickListener(v -> {
            // Open image picker
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
    }

    public void setData(Employee employee){
        String imageUrl = "https://hotel-service-manage.pockethost.io/api/files/s1fvh4cvz1v4k80/"+employee.getId()+"/"+employee.getProfileImage()+"?token=";
        Glide.with(EditProfileActivity.this)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(ivAccountAvt);
        etFirstName.setText(employee.getFirstName());
        etLastName.setText(employee.getLastName());
        LocalDateTime dob = employee.getDob(); // Assuming getDateOfBirth() returns LocalDateTime
        if (dob != null) {
            String formattedDate = DateUtil.localDateTimeToString(dob);
            etDateOfBirth.setText(formattedDate);
        } else {
            etDateOfBirth.setText(""); // Handle case where DOB is null
        }
        etPhoneNumber.setText(employee.getPhoneNumber());
        String gender = employee.getGender(); // Get gender from employee
        if ("male".equalsIgnoreCase(gender)) {
            rgGender.check(R.id.rb_male); // Assuming rb_male is the id for Male RadioButton
        } else if ("female".equalsIgnoreCase(gender)) {
            rgGender.check(R.id.rb_female); // Assuming rb_female is the id for Female RadioButton
        } else {
            rgGender.clearCheck(); // Optional: Clear the selection if the gender is not recognized
        }
        etAddress.setText(employee.getAddress());
        btnSave.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                // Update image first
                updateImage(employee);
            } else {
                // Directly update profile without image
                setUpdate(employee);
            }
        });
    }

    private void updateImage(Employee employee) {
        if (selectedImageUri != null) {
            Bitmap bitmap = null;
            try {
                bitmap = ((BitmapDrawable) ivAccountAvt.getDrawable()).getBitmap();
                MultipartBody.Part avtImage = ImageLoaderUtil.bitmapToPart(this, bitmap);
                // Initialize ViewModel
                editProfileActivityViewModel = new EditProfileActivityViewModel();
                editProfileActivityViewModel.updateProfileImage(employee.getId(), avtImage);
                // After updating image, update profile data
                setUpdate(employee);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to update image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setUpdate(Employee employee){
        boolean isValid = true;

        String fullName = etFirstName.getText().toString() + " " + etLastName.getText().toString();
        if(!ValidateUtil.isNameValid(fullName)){
            etFirstName.setError("First name is required, full name must at least 5 characters");
            etLastName.setError("Last name is required, full name must at least 5 characters");
            isValid = false;
        } else{
            employee.setFirstName(etFirstName.getText().toString());
            employee.setLastName(etLastName.getText().toString());
        }

        if(!ValidateUtil.isDateOfBirthValid(etDateOfBirth)){
            isValid = false;
        } else{
            employee.setDob(DateUtil.parseToLocalDateTime(etDateOfBirth.getText().toString()));
        }

        if(!ValidateUtil.isPhoneValid(etPhoneNumber)){
            isValid = false;
        } else{
            employee.setPhoneNumber(etPhoneNumber.getText().toString());
        }
        int checkedId = rgGender.getCheckedRadioButtonId();
        if(checkedId != -1){
            RadioButton radioButton = findViewById(checkedId);
            employee.setGender(radioButton.getText().toString());
        }

        if(!ValidateUtil.isAddressValid(etAddress)){
            isValid = false;
        } else{
            employee.setAddress(etAddress.getText().toString());
        }

        if(isValid){
            // Initialize ViewModel
            editProfileActivityViewModel = new EditProfileActivityViewModel();
            editProfileActivityViewModel.editEmployee(employee);
        } else {
            Toast.makeText(EditProfileActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }
}
