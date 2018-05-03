package com.jarq.system.dao;

import com.jarq.AbstractTest;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.managers.databaseManagers.DatabaseManager;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.managers.databaseManagers.SQLProcessManager;
import com.jarq.system.managers.databaseManagers.SQLiteManager;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.SQLiteDaoUser;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class SqlDaoFactoryTest extends AbstractTest {

    private IDaoFactory daoFactory;

    @Before
    public void setUp() {

        DatabaseManager databaseManager = mock(SQLiteManager.class);
        JDBCProcessManager processManager = mock(SQLProcessManager.class);
        IDateTimer dateTimer = mock(DateTimer.class);
        Connection connection = mock(Connection.class);

        when(databaseManager.getConnection()).thenReturn(connection);
        daoFactory = SqlDaoFactory.getInstance(databaseManager, processManager, dateTimer);
    }

    @Test
    public void getInstance() {
        assertNotNull(daoFactory);
        assertTrue(daoFactory instanceof SqlDaoFactory);
    }

    @Test
    public void createDAO_should_return_proper_dao_type() {

        IDaoUser daoUser = daoFactory.createDAO(SQLiteDaoUser.class);
        IDaoRepository daoRepository = daoFactory.createDAO(SQLiteDaoRepository.class);
        IDaoAddress daoAddress = daoFactory.createDAO(SQLiteDaoAddress.class);
        IDaoText daoText = daoFactory.createDAO(SQLiteDaoText.class);

        assertNotNull(daoUser);
        assertNotNull(daoRepository);
        assertNotNull(daoAddress);
        assertNotNull(daoText);

        // returned proper class
        assertEquals("SQLiteDaoUser", daoUser.getClass().getSimpleName());
        assertEquals("SQLiteDaoRepository", daoRepository.getClass().getSimpleName());
        assertEquals("SQLiteDaoAddress", daoAddress.getClass().getSimpleName());
        assertEquals("SQLiteDaoText", daoText.getClass().getSimpleName());
    }
}