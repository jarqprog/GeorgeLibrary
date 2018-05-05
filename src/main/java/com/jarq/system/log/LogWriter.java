package com.jarq.system.log;

import com.jarq.system.enums.LogPath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class LogWriter implements ILogWriter<String> {

    private final String textPath;

    public static ILogWriter<String> getInstance(LogPath logPath) {
        return new LogWriter(logPath);
    }

    private LogWriter(LogPath logPath) {
        this.textPath = logPath.getPath();
    }

    @Override
    public boolean write(String log) throws IOException {
        if(! checkIfFileExists(textPath) ) {
            createFile(textPath);
        }
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(textPath, true)) ) {
            writer.write(log);
        }
        return true;
    }

    private boolean checkIfFileExists(String filepath) {
        File f = new File(filepath);
        return f.exists() && !f.isDirectory();
    }

    private boolean createFile(String filepath) throws IOException {
        File path = new File(filepath);
        path.getParentFile().mkdirs();
        return path.createNewFile();
    }
}
