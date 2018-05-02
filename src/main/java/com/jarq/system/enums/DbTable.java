package com.jarq.system.enums;

public enum DbTable {

    REPOSITORIES, USERS, TEXTS, ADDRESSES;

    public String getTable() {
        return this.toString().toLowerCase();
    }
}

