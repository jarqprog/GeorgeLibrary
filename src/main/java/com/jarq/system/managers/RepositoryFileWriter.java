package com.jarq.system.managers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositoryFileWriter implements IContentWriter<String> {

    private final Charset ENCODING;

    public static IContentWriter<String> getInstance(Charset charset) {
        return new RepositoryFileWriter(charset);
    }

    private RepositoryFileWriter(Charset charset) {
        this.ENCODING = charset;
    }

    @Override
    public boolean writeContent(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING) ) {
                writer.write(content);
        }
        return true;
    }
}
