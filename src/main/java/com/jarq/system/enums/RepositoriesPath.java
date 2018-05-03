package com.jarq.system.enums;

import java.io.File;

public enum RepositoriesPath {

    FILES_REPOSITORY("src/main/resources/repositories/border/"),
    TEST_FILES_REPOSITORY("src/main/resources/testRepositories/border/"),
    READER_TEST_FILE("src/main/resources/testRepositories/readerTextFile.md"),
    WRITER_TEST_FILE("src/main/resources/testRepositories/writerTextFile.md"),
    MANAGER_PATH_HAS_FILE_TEST("src/main/resources/testRepositories/border/repositoryManager/hasFile/1.md"),
    MANAGER_PATH_CREATION_TEST("src/main/resources/testRepositories/border/repositoryManager/create/1/1/1.md"),
    MANAGER_PATH_REMOVE_FILE_TEST("src/main/resources/testRepositories/border/" +
            "repositoryManager/removeFile/1/1/1/2018_10_10_20_21_21.md");

    private String path;

    RepositoriesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path.replaceAll("/", File.separator);  // platform independent
    }
}
