package com.jarq.system.managers;

import com.jarq.AbstractTest;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.RepositoryFileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RepositoryFileReaderTest extends AbstractTest {

    private IContentReader<String> contentReader;
    private String testFile = RepositoriesPath.TEST_FILE.getPath();

    @Before
    public void setUp() {
        contentReader = RepositoryFileReader.getInstance(RepositoriesPath.TEST_FILES_REPOSITORY);
    }

    @Test
    public void getInstance() {
        assertNotNull(contentReader);
        assertTrue(contentReader instanceof RepositoryFileReader);
    }

    @Test
    public void readContent() throws IOException {

        String expected = "test TEST\n" +
                "test\n";
        String output = contentReader.readContent(testFile);

        assertEquals(expected, output);
    }
}
