package com.example.hsb;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;

public class App extends Application {
    private static App mSelf;
    private Gson mGSon;
    public static final String SERVICE_CHANNEL_ID = "service";
    private static final String SERVICE_CHANNEL_NAME = "Service";

    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGSon = new Gson();
//        createNotificationServiceChannel();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

//    private void createNotificationServiceChannel() {
//        NotificationChannel notificationChannel = new NotificationChannel(
//                SERVICE_CHANNEL_ID,
//                SERVICE_CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_HIGH
//        );
//        NotificationManager manager = getSystemService(NotificationManager.class);
//        manager.createNotificationChannel(notificationChannel);
//    }

    public Gson getGSon() {
        return mGSon;
    }
}
