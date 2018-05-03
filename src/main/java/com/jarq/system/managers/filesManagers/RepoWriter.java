package com.jarq.system.managers.filesManagers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepoWriter extends RepositoryFile implements IContentWriter<String> {

    public static IContentWriter<String> getInstance(Charset charset) {
        return new RepoWriter(charset);
    }

    private RepoWriter(Charset charset) {
        super(charset);
    }

    @Override
    public boolean writeContent(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, getEncoding()) ) {
                writer.write(content);
        }
        return true;
    }
}
