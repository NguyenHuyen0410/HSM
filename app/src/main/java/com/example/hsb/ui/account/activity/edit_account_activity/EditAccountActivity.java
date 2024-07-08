package com.example.hsb.ui.account.activity.edit_account_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.Arrays;

public class EditAccountActivity extends AppCompatActivity {
    private String[] statusItems = {"ACTIVE", "TERMINATE"};
    private String[] roleItems = {"MANAGER", "RECEPTIONIST", "CUSTOMER"};
    private EditText name;
    private EditText password;
    private EditText passwordConfirm;
    private EditText email;
    private AutoCompleteTextView autoCompleteStatus;
    private AutoCompleteTextView autoCompleteRole;
    private Button saveBtn;
    private Button deleteBtn;
    private EditAccountActivityViewModel editAccountActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);
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

        setLayout();

        // Initialize ViewModel
        editAccountActivityViewModel = new ViewModelProvider(this).get(EditAccountActivityViewModel.class);

        // Get the account passed to the activity
        Account account = (Account) getIntent().getSerializableExtra("account");
        if (account != null) {
            setData(account);
        } else {
            account = new Account();
        }

        // Set up role AutoCompleteTextView
        ArrayAdapter<String> arrayRoleAdapter = new ArrayAdapter<>(this, R.layout.list_role_item, roleItems);
        autoCompleteRole.setAdapter(arrayRoleAdapter);

        // Set up status AutoCompleteTextView
        ArrayAdapter<String> arrayStatusAdapter = new ArrayAdapter<>(this, R.layout.list_status_item, statusItems);
        autoCompleteStatus.setAdapter(arrayStatusAdapter);

        autoCompleteStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(EditAccountActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        Account finalAccount = account;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateData(finalAccount);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditAccountActivity.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete this account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Call ViewModel to delete account
                                editAccountActivityViewModel.deleteAccount(finalAccount.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        // Observe the ViewModel for delete status
        editAccountActivityViewModel.getDeleteStatusLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDeleted) {
                if (isDeleted != null && isDeleted) {
                    Toast.makeText(EditAccountActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        // Observe the ViewModel for account updates
        editAccountActivityViewModel.getAccountLiveData().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account updatedAccount) {
                // Handle the updated account, e.g., show a message or update UI
                Toast.makeText(EditAccountActivity.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                // Optionally finish the activity or update the UI further
                finish();
            }
        });

        // Observe the ViewModel for toast messages
        editAccountActivityViewModel.getToastMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(EditAccountActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setLayout(){
        name = findViewById(R.id.et_user_name);
        password = findViewById(R.id.et_password);
        passwordConfirm = findViewById(R.id.et_confirm_password);
        email = findViewById(R.id.et_email);
        autoCompleteRole = findViewById(R.id.auto_complete_role);
        autoCompleteStatus = findViewById(R.id.auto_complete_status);
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

    public void setUpdateData(@Nullable Account account) {
        boolean isValid = true;

        // Validate name
        if (ValidateUtil.isNameValid(name)) {
            account.setName(name.getText().toString());
        } else {
            name.setError("Invalid name");
            isValid = false;
        }

        // Validate password and password confirmation
        if (!ValidateUtil.isPassEqual(password, passwordConfirm)) {
            passwordConfirm.setError("Passwords do not match");
            isValid = false;
        } else if (!ValidateUtil.isPasswordValid(password)) {
            password.setError("Invalid password");
            isValid = false;
        } else {
            account.setPassword(password.getText().toString());
        }

        // Validate email
        if (ValidateUtil.isEmailValid(email)) {
            account.setEmail(email.getText().toString());
        } else {
            email.setError("Invalid email");
            isValid = false;
        }

        // Validate role
        String updatedRole = autoCompleteRole.getText().toString();
        switch (updatedRole) {
            case "RECEPTIONIST":
                account.setRole(SystemRoles.RECEPTIONIST);
                break;
            case "MANAGER":
                account.setRole(SystemRoles.MANAGER);
                break;
            case "CUSTOMER":
                account.setRole(SystemRoles.CUSTOMER);
                break;
            default:
                autoCompleteRole.setError("Invalid role");
                isValid = false;
                break;
        }

        // Validate status
        String updatedStatus = autoCompleteStatus.getText().toString();
        if (Arrays.asList(statusItems).contains(updatedStatus)) {
            account.setAccountStatus(updatedStatus);
        } else {
            autoCompleteStatus.setError("Invalid status");
            isValid = false;
        }

        if (isValid) {
            // Call ViewModel to update or create account
            if (account.getId() != null) {
                editAccountActivityViewModel.editAccount(account);
            } else {
                editAccountActivityViewModel.createAccount(account);
            }
        } else {
            Toast.makeText(EditAccountActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(@Nullable Account account){
        name.setText(account.getName());
        password.setText(account.getPassword()); // Assuming password is retrievable, otherwise, handle appropriately
        passwordConfirm.setText(account.getPassword());
        email.setText(account.getEmail());
        autoCompleteRole.setText(account.getRole().getName(), false);
        autoCompleteStatus.setText(account.getAccountStatus(), false);
    }
}