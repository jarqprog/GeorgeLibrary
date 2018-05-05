package com.jarq.system.service;

import com.jarq.system.log.ILog;

import java.util.Arrays;

public abstract class Service implements IService {

    private final ILog log;

    protected Service(ILog log) {
        this.log = log;
    }

    private String getStackTrace() {
        return Arrays.toString(Thread.currentThread().getStackTrace());
    }

    protected void reportException(Exception ex) {
        String report = "## EXCEPTION ## " + ex.getMessage() + "\nTrace: " + getStackTrace();
        log.log(report);
    }

    protected void report(String message) {
        String report = message + "\nTrace: " + getStackTrace();
        log.log(message);
    }
}
