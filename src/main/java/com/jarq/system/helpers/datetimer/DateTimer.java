package com.jarq.system.helpers.datetimer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimer implements IDateTimer {

    private final String formatter;

    public DateTimer(String formatter) {
        this.formatter = formatter;
    }

    public DateTimer() {
        this.formatter = "yyyy-MM-dd HH:mm:ss";
    }

    @Override
    public String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(formatter);
        return now.format(format);
    }
}
