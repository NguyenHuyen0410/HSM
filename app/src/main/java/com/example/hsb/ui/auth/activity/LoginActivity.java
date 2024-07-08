package com.example.hsb.ui.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.hsb.R;
import com.example.hsb.entities.Account_Fix;
import com.example.hsb.repository.AuthRepository;
import com.example.hsb.ui.SplashActivity;
import com.example.hsb.ui.auth.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView tvForgotPassword;
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
        tvForgotPassword = findViewById(R.id.login_forgot_password);
    }

    private void bindingAction() {
        loginButton.setOnClickListener(this::onClickLogin);
        tvForgotPassword.setOnClickListener(this::onClickForgotPassword);
    }

    private void onClickForgotPassword(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void onClickLogin(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Account_Fix account = new Account_Fix();
        account.setUsername(username);
        account.setPassword(password);
        AuthRepository.getInstance().login(account).observe(this, status -> {
            if (status.equals("success")) {
                Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}