package com.jarq.system.log;

import com.jarq.system.FileRelatedTest;
import com.jarq.system.enums.LogPath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LogWriterTest extends FileRelatedTest {

    private ILogWriter<String> writer;
    private final String path = LogPath.TEST_LOG.getPath();

    @Before
    public void setUp() {
        writer = LogWriter.getInstance(LogPath.TEST_LOG);
    }

    @Test
    public void getInstance() {
        assertNotNull(writer);
        assertTrue(writer instanceof LogWriter);
    }

    @Test
    public void write() throws IOException {

        removePath(path);  // remove old file
        String report = "Exception occurred.";

        writer.write(report);
        String output = readTestFile(path);

        assertEquals(report, output);
    }
}