package com.jarq.system.enums;

public enum DbTables {

    REPOSITORIES, USERS, NOTES, ADDRESSES;

    public String getTable() {
        return this.toString().toLowerCase();
    }
}

