package com.jarq.system.enums;

import java.io.File;

public enum LogPath {

    SYSTEM_LOG("src/main/resources/logs/systemLog/log.md");

    private String path;

    LogPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path.replaceAll("/", File.separator);  // platform independent
    }
}