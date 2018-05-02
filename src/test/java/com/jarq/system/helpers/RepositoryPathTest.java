package com.jarq.system.helpers;

import com.jarq.AbstractTest;
import com.jarq.system.enums.FileExtension;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.helpers.repositoryPath.RepositoryPath;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RepositoryPathTest extends AbstractTest {

    private IRepositoryPath repositoryPath;

    @Before
    public void setUp() {
        repositoryPath = RepositoryPath.getInstance(RepositoriesPath.FILES_REPOSITORY, FileExtension.MD);
    }

    @Test
    public void getInstance() {
        assertNotNull(repositoryPath);
        assertTrue(repositoryPath instanceof RepositoryPath);
    }

    @Test
    public void build() {

        String sep = File.separator;
        String baseDirectory = RepositoriesPath.FILES_REPOSITORY.getPath();
        int userId = 10;
        int repositoryId = 1;
        int textId = 5;
        String modificationDate = "2011-10-08 10:22:01";
        String fileExtension = FileExtension.MD.getExtension();

        String expected = baseDirectory + "10" + sep + "1" + sep +
                "5" + sep + "2011_10_08_10_22_01" + fileExtension;

        String output = "";
//        String output = repositoryPath.buildFullPath(userId, repositoryId, textId, modificationDate);

        assertEquals(expected, output);
    }
}