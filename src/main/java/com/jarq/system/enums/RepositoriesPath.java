package com.jarq.system.enums;

public enum RepositoriesPath {

    FILES_REPOSITORY("src/main/resources/repositories/");

    private String path;

    RepositoriesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
