package com.example.hsb.ui.base.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hsb.R;

import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    private static BaseActivity mSelf;
    private Gson mGSon;
    public static BaseActivity self() {
        return mSelf;
    }
    public Gson getGSon() {
        return mGSon;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
    }
}