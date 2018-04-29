package com.jarq.system.enums;

public enum DbTables {

    REPOSITORIES, USERS, TEXTS, ADDRESSES;

    public String getTable() {
        return this.toString().toLowerCase();
    }
}

