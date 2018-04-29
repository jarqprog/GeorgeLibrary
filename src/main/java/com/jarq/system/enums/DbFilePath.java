package com.jarq.system.enums;

public enum DbFilePath {

    DB_SETUP_SCRIPT("src/main/resources/setup_script.sql"),
    SQLITE_DATABASE("src/main/resources/repository.db");

    private String filePath;

    DbFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getPath(){
        return filePath;
    }
}
