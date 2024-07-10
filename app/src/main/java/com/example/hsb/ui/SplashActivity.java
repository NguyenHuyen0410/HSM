package com.example.hsb.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.record.AccountRecord;
import com.example.hsb.repository.AuthRepository;
import com.example.hsb.storage.SharedPrefManager;
import com.example.hsb.ui.auth.activity.LoginActivity;
import com.example.hsb.ui.category.fragment.CategoryFragment;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onTokenRefresh();
    }

    private void onTokenRefresh() {
        AuthRepository.getInstance().refreshToken().observe(this, status -> {
            AccountRecord account = SharedPrefManager.getInstance().get("account", AccountRecord.class);
            if (account != null) {
                switch (status) {
                    case "MANAGER":
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "Chào mừng MANAGER - " + account.getUsername(), Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "RECEPTIONIST":
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "Chào mừng RECEPTIONIST - " + account.getUsername(), Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "CUSTOMER":
                        intent = new Intent(SplashActivity.this, CategoryFragment.class);
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "Chào mừng CUSTOMER", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "TERMINATE":
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        Toast.makeText(SplashActivity.this, "Tài khoản đã bị chấm dứt, xin mời đăng nhập lại", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                        break;
                    case "ISDELETED":
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        Toast.makeText(SplashActivity.this, "Tài khoản đã bị xoá, xin mời đăng nhập lại", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                        break;
                    case "FAILED":
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(SplashActivity.this, "Xin mời bạn đăng nhập lại", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            }else{
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
