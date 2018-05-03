package com.jarq.system.helpers.repositoryPath;

import com.jarq.AbstractTest;
import com.jarq.system.enums.FileExtension;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.repository.Repository;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.text.Text;
import com.jarq.system.models.user.IUser;
import com.jarq.system.models.user.User;
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

        String transformedDate = "2018_10_10_12_00_00";

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

        IText text = mock(Text.class);

        when(text.getUserId()).thenReturn(1);
        when(text.getRepositoryId()).thenReturn(1);
        when(text.getId()).thenReturn(3);

        String expected = String.format("%s1%s1%s3%s",
                pathBegin,
                separator,
                separator,
                separator);

        String filepath = repositoryPath.textDir(text);

        assertEquals(expected, filepath);
    }

    @Test
    public void repositoryDir() {

        IRepository repository = mock(Repository.class);

        when(repository.getUserId()).thenReturn(1);
        when(repository.getId()).thenReturn(1);

        String expected = String.format("%s1%s1%s",
                pathBegin,
                separator,
                separator);

        String filepath = repositoryPath.repositoryDir(repository);

        assertEquals(expected, filepath);

    }

    @Test
    public void userDir() {

        IUser user = mock(User.class);

        when(user.getId()).thenReturn(1);

        String expected = String.format("%s1%s",
                pathBegin,
                separator);

        String filepath = repositoryPath.userDir(user);

        assertEquals(expected, filepath);
    }
}