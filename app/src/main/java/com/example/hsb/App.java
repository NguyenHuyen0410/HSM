package com.example.hsb;

import android.app.Application;
import com.google.gson.Gson;

public class App extends Application {
    private static App mSelf;
    private Gson mGSon;
    public static App self() {
        return mSelf;
    }
    public Gson getGSon() {
        return mGSon;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGSon = new Gson();
    }
}