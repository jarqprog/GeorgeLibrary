package com.jarq.system.service;

import com.jarq.AbstractTest;
import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.dao.SqlDaoFactory;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.helpers.repositoryPath.RepositoryPath;
import com.jarq.system.managers.filesManagers.*;

import com.jarq.system.policy.*;
import com.jarq.system.service.address.AddressService;
import com.jarq.system.service.address.IAddressService;
import com.jarq.system.service.repository.IRepoService;
import com.jarq.system.service.repository.RepoService;
import com.jarq.system.service.text.ITextService;
import com.jarq.system.service.text.TextService;
import com.jarq.system.service.user.IUserService;
import com.jarq.system.service.user.UserService;
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
        IRepositoryPath repositoryPath = mock(RepositoryPath.class);
        IDateTimer dateTimer = mock(DateTimer.class);
        IEmailPolicy emailPolicy = mock(EmailPolicy.class);
        IPasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
        IAddressPolicy addressPolicy = mock(AddressPolicy.class);

        serviceFactory = ServiceFactory.getInstance(daoFactory,
                repositoryManager, contentReader, contentWriter,
                repositoryPath, dateTimer, emailPolicy,
                passwordPolicy, addressPolicy);
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

        IRepoService repoService = serviceFactory
                .createSQLiteService(RepoService.class);

        IAddressService addressService = serviceFactory
                .createSQLiteService(AddressService.class);

        assertNotNull(userService);
        assertNotNull(textService);
        assertNotNull(repoService);
        assertNotNull(addressService);

        // returned proper class
        assertEquals("UserService", userService.getClass().getSimpleName());
        assertEquals("TextService", textService.getClass().getSimpleName());
        assertEquals("RepoService", repoService.getClass().getSimpleName());
        assertEquals("AddressService", addressService.getClass().getSimpleName());
    }
}