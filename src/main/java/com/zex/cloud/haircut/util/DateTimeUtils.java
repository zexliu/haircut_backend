package com.zex.cloud.haircut.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static LocalTime localDateTimeToLocalTime(LocalDateTime localDateTime){
        return LocalTime.of(localDateTime.getHour(),localDateTime.getMinute(),localDateTime.getSecond());
    }

    public static String format(LocalDateTime expireAt, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(expireAt);
    }
}
