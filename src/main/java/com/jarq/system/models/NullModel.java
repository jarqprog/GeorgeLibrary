package com.jarq.system.models;

public abstract class NullModel implements Identifiable {

    private final int id = -1;  // common for all null models

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {}

    protected String getNotAvailable() {
        return "n/a";
    }

    @Override
    public String toString() {
        return "NullModel{" +
                "id=" + id +
                '}';
    }
}
