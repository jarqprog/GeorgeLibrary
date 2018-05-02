package com.jarq.system.managers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositoryFileReader implements IContentReader<String> {

    private final Charset ENCODING;

    public static IContentReader<String> getInstance(Charset charset) {
        return new RepositoryFileReader(charset);
    }

    private RepositoryFileReader(Charset charset) {
        this.ENCODING = charset;
    }

    @Override
    public String readContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
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
