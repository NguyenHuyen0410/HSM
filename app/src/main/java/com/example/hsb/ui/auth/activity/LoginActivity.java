package com.example.hsb.ui.auth.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.R;

public class LoginActivity extends AppCompatActivity {
    private EditText loginIdentify;
    private EditText loginPassword;
    private Button btnLogin;
    private TextView loginForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bindingView();
        handlingAction();
    }

    private void bindingView(){
        loginIdentify = findViewById(R.id.login_identify);
        loginPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_signin);
        loginForgotPassword = findViewById(R.id.login_forgot_password);
    }

    private void handlingAction() {
        btnLogin.setOnClickListener(this::onClickLogin);
        loginForgotPassword.setOnClickListener(this::onClickForgotPassword);
    }

    private void onClickLogin(View view){
        String identify = loginIdentify.getText().toString();
        String password = loginPassword.getText().toString();
        if(identify.isEmpty() || password.isEmpty()){
            return;
        }
    }

    private void onClickForgotPassword(View view){
        // Handle forgot password
    }
}