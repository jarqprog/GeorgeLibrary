package com.jarq.system.enums;

public enum DbTable {

    REPOSITORIES, USERS, TEXTS, ADDRESSES, CONTENTS;

    public String getTable() {
        return this.toString().toLowerCase();
    }
}

