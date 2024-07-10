package com.example.hsb.ui.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.hsb.R;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.ui.account.activity.ListAccountActivity;
import com.example.hsb.ui.auth.viewmodel.AuthViewModel;
import com.example.hsb.ui.category.fragment.CategoryFragment;

public class LoginActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindingView();
        bindingAction();
    }

    private void bindingView() {
        usernameEditText = findViewById(R.id.edt_login_username);
        passwordEditText = findViewById(R.id.edt_login_password);
        loginButton = findViewById(R.id.btn_login);
    }

    private void bindingAction() {
        loginButton.setOnClickListener(this::onClickLogin);
    }

    private void onClickLogin(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        AccountRecord account = new AccountRecord();
        account.setUsername(username);
        account.setPassword(password);
        authViewModel.login(account).observe(this, s -> {
            switch (s) {
                case "TERMINATE":
                    Toast.makeText(LoginActivity.this, "Tài khoản đã bị chấm dứt", Toast.LENGTH_SHORT).show();
                    break;
                case "ISDELETED":
                    Toast.makeText(LoginActivity.this, "Tài khoản đã bị xoá", Toast.LENGTH_SHORT).show();
                    break;
                case "MANAGER":
                    intent = new Intent(this, ListAccountActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công: MANAGER", Toast.LENGTH_SHORT).show();
                    break;
                case "RECEPTIONIST":
                    intent = new Intent(this, CategoryFragment.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công: RECEPTIONIST", Toast.LENGTH_SHORT).show();
                    break;
                case "CUSTOMER":
                    intent = new Intent(this, CategoryFragment.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công: CUSTOMER", Toast.LENGTH_SHORT).show();
                    break;
                case "FAILED":
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}