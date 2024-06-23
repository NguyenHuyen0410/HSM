package com.example.hsb.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastWithColor(Context context, String message, int backgroundColor, int textColor) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);

        View toastView = toast.getView();
        toastView.setBackgroundColor(backgroundColor);

        TextView textView = toastView.findViewById(android.R.id.message);
        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);

        toast.show();
    }
}
