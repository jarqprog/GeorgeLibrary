package com.jarq.system.exceptions;

public class DaoFailure extends Exception {

    private final String exceptionInfo = "Problem occurred in DAO action. ";
    private final String message;

    public DaoFailure(String message) {
        this.message = exceptionInfo + message;
    }

    public DaoFailure() {
        this.message = exceptionInfo;
    }

    public String getMessage() {
        return message;
    }

}
