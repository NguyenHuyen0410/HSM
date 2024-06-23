package com.example.hsb.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
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

    public static LocalDateTime stringToLocalDateTime(String l){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(DEFAULT_DATE_TIME_FORMAT)
                .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true)
                .appendPattern("'Z'")
                .toFormatter();

        return LocalDateTime.parse(l, formatter);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(DEFAULT_DATE_TIME_FORMAT)
                .appendFraction(ChronoField.NANO_OF_SECOND, 3, 3, true)
                .appendLiteral('Z')
                .toFormatter();

        return localDateTime.format(formatter);
    }
}
