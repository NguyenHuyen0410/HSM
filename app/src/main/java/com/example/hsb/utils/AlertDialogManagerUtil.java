package com.example.hsb.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

//Class này cho phép bạn tạo và hiển thị hộp thoại cảnh báo đơn giản hoặc hộp
// thoại xác nhận với các tùy chọn nút OK và Cancel. Bạn có thể tùy chỉnh nội dung và
// hành động của các nút theo ý muốn.
public class AlertDialogManagerUtil {
    private Context context;

    public AlertDialogManagerUtil(Context context) {
        this.context = context;
    }

    public void showAlertDialog(String title, String message, String positiveButton, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButton, positiveClickListener);
        builder.setCancelable(false); // Không cho phép hủy bỏ hộp thoại bằng cách nhấn nút Back

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showConfirmationDialog(String title, String message, String positiveButton, String negativeButton,
                                       DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButton, positiveClickListener);
        builder.setNegativeButton(negativeButton, negativeClickListener);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
