package com.example.hsb.ui.account.activity.edit_account_activity;

import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.storage.SystemRoles;
import com.example.hsb.utils.ValidateUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class EditAccountActivity extends AppCompatActivity {
    private final String[] statusItems = {"ACTIVE", "TERMINATE"};
    private final String[] roleItems = {"MANAGER", "RECEPTIONIST", "CUSTOMER"};
    private EditText name, password, passwordConfirm, oldPassword, email;
    private AutoCompleteTextView autoCompleteStatus, autoCompleteRole;
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
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        }
        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        editAccountActivityViewModel = new EditAccountActivityViewModel();

        // Get the account passed to the activity
        Account account = (Account) getIntent().getSerializableExtra("account");
        bindViews();
        Button saveBtn = findViewById(R.id.btn_save);
        Button deleteBtn = findViewById(R.id.btn_delete);
        if (account != null) {
            setData(account);
        } else {
            account = new Account();
            oldPassword.setVisibility(View.GONE);
        }
        Account finalAccount = account;
        saveBtn.setOnClickListener(v -> setUpdateData(finalAccount));

        deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(EditAccountActivity.this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this account?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Call ViewModel to delete account
                    editAccountActivityViewModel.deleteAccount(finalAccount.getId());
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        // Set up role AutoCompleteTextView
        ArrayAdapter<String> arrayRoleAdapter = new ArrayAdapter<>(this, R.layout.list_role_item, roleItems);
        autoCompleteRole.setAdapter(arrayRoleAdapter);

        // Set up status AutoCompleteTextView
        ArrayAdapter<String> arrayStatusAdapter = new ArrayAdapter<>(this, R.layout.list_status_item, statusItems);
        autoCompleteStatus.setAdapter(arrayStatusAdapter);

        autoCompleteStatus.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            Toast.makeText(EditAccountActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
        });

        // Observe the ViewModel for delete status
        editAccountActivityViewModel.getDeleteStatusLiveData().observe(this, isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Toast.makeText(EditAccountActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Observe the ViewModel for account updates
        editAccountActivityViewModel.getAccountLiveData().observe(this, updatedAccount -> {
            // Handle the updated account, e.g., show a message or update UI
            Toast.makeText(EditAccountActivity.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
            // Optionally finish the activity or update the UI further
            finish();
        });

        // Observe the ViewModel for toast messages
        editAccountActivityViewModel.getToastMessageLiveData().observe(this, message -> Toast.makeText(EditAccountActivity.this, message, Toast.LENGTH_SHORT).show());
    }
    private void bindViews() {
        name = findViewById(R.id.et_user_name);
        password = findViewById(R.id.et_password);
        passwordConfirm = findViewById(R.id.et_confirm_password);
        oldPassword = findViewById(R.id.et_old_password);
        email = findViewById(R.id.et_email);
        autoCompleteRole = findViewById(R.id.auto_complete_role);
        autoCompleteStatus = findViewById(R.id.auto_complete_status);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back to previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpdateData(@NotNull Account account) {
        boolean isValid = true;
        // Validate name
        if (ValidateUtil.isNameValid(name)) {
            account.setName(name.getText().toString());
        } else {
            isValid = false;
        }

        // Validate password and password confirmation
        if (!ValidateUtil.isPassEqual(password, passwordConfirm)) {
            isValid = false;
        } else if (!ValidateUtil.isPasswordValid(password)) {
            isValid = false;
        } else {
            account.setPassword(password.getText().toString());
        }

        account.setOldPassword(oldPassword.getText().toString());

        // Validate email
        if (ValidateUtil.isEmailValid(email)) {
            account.setEmail(email.getText().toString());
        } else {
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

    public void setData(@NotNull Account account){
        name.setText(account.getName());
        password.setText(account.getPassword()); // Assuming password is retrievable, otherwise, handle appropriately
        passwordConfirm.setText(account.getPassword());
        oldPassword.setText(account.getOldPassword());
        email.setText(account.getEmail());
        autoCompleteRole.setText(account.getRole().getName(), false);
        autoCompleteStatus.setText(account.getAccountStatus(), false);
    }
}