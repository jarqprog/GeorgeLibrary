package com.jarq.system.managers.filesManagers;

import com.jarq.AbstractTest;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.helpers.repositoryPath.RepositoryPath;
import com.jarq.system.models.content.Content;
import com.jarq.system.models.content.IContent;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryManagerTest extends AbstractTest {

    private IRepositoryManager repositoryManager;
    private IContent content;

    private IRepositoryPath repositoryPath;

    @Before
    public void setUp() throws Exception {

        content = mock(Content.class);
        repositoryPath = mock(RepositoryPath.class);
        repositoryManager = RepositoryManager.getInstance(repositoryPath);
    }

    @Test
    public void getInstance() {
        assertNotNull(repositoryManager);
        assertTrue(repositoryManager instanceof RepositoryManager);
    }

    @Test
    public void hasFile_should_be_true() {

        String filepath = RepositoriesPath.MANAGER_PATH_HAS_FILE_TEST.getPath();

        when(content.getFilepath()).thenReturn(filepath);

        assertTrue(repositoryManager.hasFile(content));

    }

    @Test
    public void hasFile_should_be_false() {

        String fakePath = "123";

        when(content.getFilepath()).thenReturn(fakePath);

        assertFalse(repositoryManager.hasFile(content));
    }

    @Test
    public void hasFile1_should_be_true() {

        String filepath = RepositoriesPath.MANAGER_PATH_HAS_FILE_TEST.getPath();

        assertTrue(repositoryManager.hasFile(filepath));

    }

    @Test
    public void hasFile1_should_be_false() {

        String fakePath = RepositoriesPath.MANAGER_PATH_HAS_FILE_TEST.getPath()+"1a";

        assertFalse(repositoryManager.hasFile(fakePath));
    }

    @Test
    public void createFile() throws Exception {

        String filepath = RepositoriesPath.MANAGER_PATH_CREATION_TEST.getPath();
        removePath(filepath);

        when(content.getFilepath()).thenReturn(filepath);

        if(checkIfFileExists(filepath)) {
            throw new Exception("Can't continue test, test file wasn't removed!");
        }

        assertTrue(repositoryManager.createFile(content));
        assertTrue(checkIfFileExists(filepath));

    }

    @Test
    public void removeFile() throws IOException {
        String pathToRemove = RepositoriesPath.MANAGER_PATH_REMOVE_FILE_TEST.getPath();

        System.out.println(createFile(pathToRemove));
    }

    @Test
    public void removeTextDirectory() {
    }

    @Test
    public void removeRepository() {
    }

    private void removePath(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        deleteIfExists(path);
    }

    private boolean createFile(String filepath) throws IOException {
        File path = new File(filepath);
        path.getParentFile().mkdirs();
        return path.createNewFile();
    }

    private boolean checkIfFileExists(String filepath) {
        File f = new File(filepath);
        return f.exists() && !f.isDirectory();
    }


}