package com.jarq.system.helpers.datetimer;

import com.jarq.system.enums.DateTimerFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimer implements IDateTimer {

    private final String formatter;

    public static IDateTimer getInstane(DateTimerFormatter dateTimerFormatter) {
        return new DateTimer(dateTimerFormatter.getFormatter());
    }

    public static IDateTimer getInstane() {
        return new DateTimer();
    }

    DateTimer(String formatter) {
        this.formatter = formatter;
    }

    DateTimer() {
        this.formatter = "yyyy-MM-dd HH:mm:ss";
    }

    @Override
    public String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(formatter);
        return now.format(format);
    }
}