package com.jarq.system.enums;

import java.io.File;

public enum LogPath {

    LOGGER("src/main/resources/logs/log.md");

    private String path;

    LogPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path.replaceAll("/", File.separator);  // platform independent
    }
}