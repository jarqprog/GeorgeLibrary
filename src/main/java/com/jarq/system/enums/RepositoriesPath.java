package com.jarq.system.enums;

public enum RepositoriesPath {

    FILES_REPOSITORY("src/main/resources/repositories/"),
    TEST_FILES_REPOSITORY("src/main/resources/testRepositories/"),
    TEST_FILE("test");

    private String path;

    RepositoriesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
