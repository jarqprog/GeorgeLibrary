package com.jarq.system.exceptions;

public class DaoFailure extends Exception {

    private final String message;

    public DaoFailure(String message) {
        this.message = message;
    }

    public DaoFailure() {
        this.message = "Problem occurred in DAO action";
    }

    public String getMessage() {
        return message;
    }

}
