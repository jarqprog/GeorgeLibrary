package com.jarq.system.enums;

import java.io.File;

public enum RepositoriesPath {

    FILES_REPOSITORY("src/main/resources/repositories/"),
    TEST_FILES_REPOSITORY("src/main/resources/testRepositories/"),
    TEST_FILE("testFile.md"),
    READER_TEST_FILE("src/main/resources/testRepositories/readerTextFile.md"),
    WRITER_TEST_FILE("src/main/resources/testRepositories/writerTextFile.md"),
    MANAGER_PATH_INSPECT_TEST("src/main/resources/testRepositories/pathManager/inspect/1.md"),
    MANAGER_PATH_CREATION_TEST("src/main/resources/testRepositories/pathManager/create/1.md"),
    MANAGER_PATH_DEMOLISH_TEST("src/main/resources/testRepositories/pathManager/demolish/1.md");

    private String path;

    RepositoriesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path.replaceAll("/", File.separator);  // platform independent
    }
}
