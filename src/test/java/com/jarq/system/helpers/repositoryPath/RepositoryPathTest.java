package com.jarq.system.helpers.repositoryPath;

import com.jarq.AbstractTest;
import com.jarq.system.enums.FileExtension;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.text.Text;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryPathTest extends AbstractTest {

    private IRepositoryPath repositoryPath;
    private final String pathBegin = RepositoriesPath.TEST_FILES_REPOSITORY.getPath();
    private final String fileExtension = FileExtension.MD.getExtension();
    private final String separator = File.separator;
    private final String transformedDate = "2018_10_10_12_00_00";

    @Before
    public void setUp() {
        repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.TEST_FILES_REPOSITORY, FileExtension.MD);
    }

    @Test
    public void getInstance() {
        assertNotNull(repositoryPath);
        assertTrue(repositoryPath instanceof RepositoryPath);
    }

    @Test
    public void filepath() {
        IText text = mock(Text.class);
        when(text.getUserId()).thenReturn(1);
        when(text.getRepositoryId()).thenReturn(1);
        when(text.getId()).thenReturn(3);
        when(text.getModificationDate()).thenReturn(transformedDate);

        String expected = String.format("%s1%s1%s3%s%s%s",
                pathBegin,
                separator,
                separator,
                separator,
                transformedDate,
                fileExtension);

        String filepath = repositoryPath.filepath(text);

        assertEquals(expected, filepath);
    }

    @Test
    public void textDir() {
    }

    @Test
    public void repositoryDir() {
    }

    @Test
    public void userDir() {
    }
}