package com.example.hsb.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String MODIFIED_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static LocalDateTime apiDateTimeStringToLocalDateTime(String l) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(DEFAULT_DATE_TIME_FORMAT)
                .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true)
                .appendPattern("'Z'")
                .toFormatter();

        return LocalDateTime.parse(l, formatter);
    }

    public static LocalDateTime parseToLocalDateTime(String dateTimeStr) {
        // Define the DateTimeFormatter with the expected pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MODIFIED_DATE_TIME_FORMAT);

        // Parse the string to LocalDateTime using the formatter
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(MODIFIED_DATE_TIME_FORMAT)
                .toFormatter();

        return localDateTime.format(formatter);
    }

    public static String localDateTimeToJsonFormat(LocalDateTime localDateTime) {
        // Convert LocalDateTime to ZonedDateTime in UTC
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
        // Format ZonedDateTime to ISO 8601 format
        return FORMATTER.format(zonedDateTime);
    }

}
