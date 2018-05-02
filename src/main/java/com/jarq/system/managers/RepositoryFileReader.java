package com.jarq.system.managers;

import com.jarq.system.enums.RepositoriesPath;

import java.io.*;

public class FileContent implements IContentReader<String> {

    private final String repositoryPath;

    public static IContentReader<String> getInstance(RepositoriesPath repositoriesPath) {
        return new FileContent(repositoriesPath);
    }

    private FileContent(RepositoriesPath repositoriesPath) {
        this.repositoryPath = repositoriesPath.getPath();
    }

    @Override
    public String readContent(String filePath) throws IOException {
        String fullPath = (repositoryPath + filePath).replaceAll("/", File.separator);
        String line;
        String nextLine = "\n";
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fullPath)))) {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line.trim());
                sb.append(nextLine);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return sb.toString();
    }

    @Override
    public String readContentAnotherWay(String filePath) throws IOException {

        return null;
    }
}
