package com.jarq.system.managers.filesManagers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextReader extends TextFileOperator implements IContentReader<String> {

    public static IContentReader<String> getInstance(Charset charset) {
        return new TextReader(charset);
    }

    private TextReader(Charset charset) {
        super(charset);
    }

    @Override
    public String readContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, getEncoding())) {
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

    @Override
    public byte[] readContentAsBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}
