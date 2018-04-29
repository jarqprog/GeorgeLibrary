package com.jarq.system.enums;

public enum DbTables {

    REPOSITORIES("repositories"), PEOPLE("people"), USERS("users"), AUTHORS("authors"),
    BOOKS("books"), NOTES("notes");

    private String table;

    DbTables(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}

