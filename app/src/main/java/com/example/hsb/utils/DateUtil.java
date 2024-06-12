package com.example.hsb.utils;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
// lớp DateUtils để cung cấp các phương thức tiện ích cho việc định dạng và chuyển đổi ngày/giờ.
// Điều này giúp bạn dễ dàng làm việc với ngày và giờ trong ứng dụng của mình.
public class DateUtil {
    // Định dạng ngày/giờ mặc định
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Định dạng ngày/giờ
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }

    // Chuyển đổi từ chuỗi sang đối tượng Date
    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseTime(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault());
        try {
            return sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDateTime(String dateTimeString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, Locale.getDefault());
        try {
            return sdf.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Định dạng ngày/giờ theo định dạng tùy chỉnh
    public static String formatCustomDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    public static Date parseCustomDate(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
