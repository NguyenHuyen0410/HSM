package com.example.hsb.ui.account.activity.edit_account_activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.hsb.entities.Account;
import com.example.hsb.storage.SystemRoles;
import com.example.hsb.utils.ValidateUtil;

public class EditAccountActivity extends AppCompatActivity {
//    private String[] statusItems = {"ACTIVE", "TERMINATE"};
//    private String[] roleItems = {"MANAGER", "RECEPTIONIST", "CUSTOMER"};
//    private EditText name;
//    private EditText password;
//    private EditText passwordConfirm;
//    private EditText email;
//    private AutoCompleteTextView autoCompleteStatus;
//    private AutoCompleteTextView autoCompleteRole;
//    private Button saveBtn;
//    private Button deleteBtn;
//    private EditAccountActivityViewModel editAccountActivityViewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.edit_account);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // Set navigation icon (arrow) to be white
//        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.arrow_white);
//        if (upArrow != null) {
//            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        }
//
//        // Enable the Up button
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//
//        name = findViewById(R.id.et_user_name);
//        password = findViewById(R.id.et_password);
//        passwordConfirm = findViewById(R.id.et_confirm_password);
//        email = findViewById(R.id.et_email);
//        autoCompleteRole = findViewById(R.id.auto_complete_role);
//        autoCompleteStatus = findViewById(R.id.auto_complete_status);
//        saveBtn = findViewById(R.id.btn_save);
//        deleteBtn = findViewById(R.id.btn_delete);
//
//        // Initialize ViewModel
//        editAccountActivityViewModel = new ViewModelProvider(this).get(EditAccountActivityViewModel.class);
//
//        // Get the account passed to the activity
//        Account account = (Account) getIntent().getSerializableExtra("account");
//        if (account != null) {
//            setCreateData(account);
//        } else{
//            account = new Account();
//        }
//
//        // Set up role AutoCompleteTextView
//        ArrayAdapter<String> arrayRoleAdapter = new ArrayAdapter<>(this, R.layout.list_role_item, roleItems);
//        autoCompleteRole.setAdapter(arrayRoleAdapter);
//
//        // Set up status AutoCompleteTextView
//        ArrayAdapter<String> arrayStatusAdapter = new ArrayAdapter<>(this, R.layout.list_status_item, statusItems);
//        autoCompleteStatus.setAdapter(arrayStatusAdapter);
//
//        autoCompleteStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                Toast.makeText(EditAccountActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Account finalAccount = account;
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setUpdateData(finalAccount);
//                if (finalAccount.getId() != null) {
//                    // Call ViewModel to update account
//                    editAccountActivityViewModel.editAccounts(finalAccount);
//                } else {
//                    // Call ViewModel to create account
//                    editAccountActivityViewModel.createAccount(finalAccount);
//                }
//            }
//        });
//
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editAccountActivityViewModel.deleteAccount(finalAccount.getId());
//            }
//        });
//
//        // Observe the ViewModel for account updates
//        editAccountActivityViewModel.getAccountLiveData().observe(this, new Observer<Account>() {
//            @Override
//            public void onChanged(Account updatedAccount) {
//                // Handle the updated account, e.g., show a message or update UI
//                Toast.makeText(EditAccountActivity.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
//                // Optionally finish the activity or update the UI further
//                finish();
//            }
//        });
//
//        // Observe the ViewModel for toast messages
//        editAccountActivityViewModel.getToastMessageLiveData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String message) {
//                Toast.makeText(EditAccountActivity.this, message, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed(); // Navigate back to previous activity
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void setUpdateData(@Nullable Account account) {
//        boolean check = true;
//        // Retrieve data from the fields
//        String updatedName = "";
//        if(check==ValidateUtil.isNameValid(name)){
//            updatedName = name.getText().toString();
//        }
//        String updatedPassword = "";
//        if(check!=ValidateUtil.isPassEqual(password, passwordConfirm)){
//
//        }
//        if(check==ValidateUtil.isPasswordValid(password)){
//            updatedPassword = password.getText().toString();
//        }
//        String updatedEmail = "";
//        if(check==ValidateUtil.isEmailValid(email)){
//            updatedEmail = email.getText().toString();
//        }
//
//        String updatedRole = autoCompleteRole.getText().toString();
//        String updatedStatus = autoCompleteStatus.getText().toString();
//
//        // Update the account
//        account.setName(updatedName);
//        account.setPassword(updatedPassword);
//        account.setEmail(updatedEmail);
//        switch (updatedRole) {
//            case "RECEPTIONIST":
//                account.setRole(SystemRoles.RECEPTIONIST);
//                break;
//            case "MANAGER":
//                account.setRole(SystemRoles.MANAGER);
//                break;
//            case "CUSTOMER":
//                account.setRole(SystemRoles.CUSTOMER);
//                break;
//        }
//        account.setAccountStatus(updatedStatus);
//    }
//
//    public void setCreateData(@Nullable Account account){
//        boolean check = true;
//        name.setText(account.getName());
//        if(check!=ValidateUtil.isNameValid(name)){
//
//        }
//        password.setText(account.getPassword()); // Assuming password is retrievable, otherwise, handle appropriately
//        if(check!=ValidateUtil.isPasswordValid(password)){
//
//        } if(check!=ValidateUtil.isPassEqual(password, passwordConfirm)){
//
//        }
//        email.setText(account.getEmail());
//        if(check!=ValidateUtil.isEmailValid(email)){
//
//        }
//        autoCompleteRole.setText(account.getRole().getName(), false);
//        autoCompleteStatus.setText(account.getAccountStatus(), false);
//    }
}