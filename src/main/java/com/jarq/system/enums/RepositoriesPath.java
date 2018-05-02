package com.jarq.system.enums;

import java.io.File;

public enum RepositoriesPath {

    FILES_REPOSITORY("src/main/resources/repositories/"),
    TEST_FILES_REPOSITORY("src/main/resources/testRepositories/"),
    READER_TEST_FILE("readerTextFile.md"),
    WRITER_TEST_FILE("writerTextFile.md");

    private String path;

    RepositoriesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path.replaceAll("/", File.separator);  // platform independent
    }
}
