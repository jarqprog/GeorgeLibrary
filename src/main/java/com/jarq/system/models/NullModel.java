package com.jarq.system.models;

public abstract class NullModel {

    private final int id = -1;  // common for all null

    public int getId() {
        return id;
    }

    public void setId(int id) {}

    protected String getNotAvailable() {
        return "n/a";
    }
}
