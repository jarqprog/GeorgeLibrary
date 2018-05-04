package com.jarq.system.service;

import com.jarq.AbstractTest;
import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.dao.SqlDaoFactory;
import com.jarq.system.managers.filesManagers.*;

import com.jarq.system.models.text.ITextService;
import com.jarq.system.models.text.TextService;
import com.jarq.system.models.user.IUserService;
import com.jarq.system.models.user.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ServiceFactoryTest extends AbstractTest {

    private IServiceFactory serviceFactory;

    @Before
    public void setUp() {
        IDaoFactory daoFactory = mock(SqlDaoFactory.class);
        IRepositoryManager repositoryManager = mock(RepositoryManager.class);
        IContentReader<String> contentReader = mock(RepoReader.class);
        IContentWriter<String> contentWriter = mock(RepoWriter.class);

        serviceFactory = ServiceFactory.getInstance(daoFactory,
                repositoryManager, contentReader, contentWriter);
    }

    @Test
    public void getInstance() {
        assertNotNull(serviceFactory);
        assertTrue(serviceFactory instanceof ServiceFactory);
    }

    @Test
    public void createSQLiteService_should_return_proper_service_type() {

        IUserService userService = serviceFactory
                .createSQLiteService(UserService.class);

        ITextService textService = serviceFactory
                .createSQLiteService(TextService.class);

        assertNotNull(userService);
        assertNotNull(textService);

        // returned proper class
        assertEquals("UserService", userService.getClass().getSimpleName());
        assertEquals("TextService", textService.getClass().getSimpleName());
    }
}