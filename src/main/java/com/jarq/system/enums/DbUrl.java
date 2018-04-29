package com.jarq.system.enums;

public enum DbUrl {

    SQLITE("jdbc:sqlite:" + DbFilePath.SQLITE_DATABASE.getPath());

    private String url;

    DbUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
