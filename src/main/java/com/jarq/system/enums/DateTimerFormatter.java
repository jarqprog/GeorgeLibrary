package com.jarq.system.enums;

public enum DateTimerFormatter {

    STANDARD("yyyy-MM-dd HH:mm:ss"),
    LOGGER("yyyy-MM-dd HH:mm:ss.SSSSSS");

    private String formatter;

    DateTimerFormatter(String formatter) {
        this.formatter = formatter;
    }

    public String getFormatter() {
        return formatter;
    }
}
