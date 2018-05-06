package com.jarq.system;

import com.jarq.AbstractTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.deleteIfExists;

public abstract class FileRelatedTest extends AbstractTest {


    protected void prepareFilepathForTest(String path) throws Exception {
        if(! checkIfFileExists(path) ) {
            if(! createFile(path) ) {
                throw new Exception("Can't continue test, path wasn't created!");
            }
        }
    }

    protected void prepareDirectoryPathForTest(String path) throws Exception {
        if(! checkIfDirectoryExists(path) ) {
            if(! createDirs(path) ) {
                throw new Exception("Can't continue test, path wasn't created!");
            }
        }
    }

    protected void removePath(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        deleteIfExists(path);
    }

    protected boolean createFile(String filepath) throws IOException {
        File path = new File(filepath);
        path.getParentFile().mkdirs();
        return path.createNewFile();
    }

    protected boolean createDirs(String dirsPath) {
        File path = new File(dirsPath);
        return path.mkdirs();
    }

    protected boolean checkIfFileExists(String filepath) {
        File f = new File(filepath);
        return f.exists() && !f.isDirectory();
    }

    protected boolean checkIfDirectoryExists(String dirsPath) {
        File dir = new File(dirsPath);
        return dir.exists() && dir.isDirectory();
    }

    protected String readTestFile(String testFilepath) throws IOException {
        Path path = Paths.get(testFilepath);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            int len = stringBuilder.length();
            stringBuilder.delete(len-1, len);  // removes unnecessary new line from the end of text
        }
        return stringBuilder.toString();
    }
}
