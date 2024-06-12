package com.example.hsb.utils;

import android.util.Log;

//ghi lại các thông báo log với nhiều mức độ khác nhau (VERBOSE, DEBUG, INFO, WARN, ERROR),
// bạn có thể sử dụng lớp Log có sẵn của Android. Lớp tiện ích này sẽ giúp bạn dễ dàng quản lý và
// ghi lại log từ các phần khác nhau của ứng dụng một cách có hệ thống.
public class LoggerUtil {
    private static final String TAG = "AppLogger";

    // Điều chỉnh mức độ log bạn muốn sử dụng
    public static final boolean IS_DEBUG = true;

    public static void v(String tag, String message) {
        if (IS_DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void v(String message) {
        if (IS_DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void d(String tag, String message) {
        if (IS_DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(String message) {
        if (IS_DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void i(String tag, String message) {
        if (IS_DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void i(String message) {
        if (IS_DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void w(String tag, String message) {
        if (IS_DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void w(String message) {
        if (IS_DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void e(String tag, String message) {
        if (IS_DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        if (IS_DEBUG) {
            Log.e(TAG, message);
        }
    }
}
