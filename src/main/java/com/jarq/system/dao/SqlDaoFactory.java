package com.jarq.dao;

import com.jarq.databaseManagers.DatabaseManager;
import com.jarq.databaseManagers.JDBCProcessManager;
import com.jarq.models.human.author.SQLiteDaoAuthor;
import com.jarq.models.human.user.SQLiteDaoUser;

import java.sql.Connection;

public class SqlDaoFactory implements IDaoFactory {

    private DatabaseManager dbManager;
    private JDBCProcessManager processManager;
    private Connection connection;

    public static IDaoFactory getInstance(DatabaseManager dbManager, JDBCProcessManager processManager) {
        return new SqlDaoFactory(dbManager, processManager);
    }

    private SqlDaoFactory(DatabaseManager dbManager, JDBCProcessManager processManager) {
        this.dbManager = dbManager;
        this.processManager = processManager;
        connection = dbManager.getConnection();
    }

    public <T extends Dao> T createDAO(Class<T> daoType) {

        if( ! dbManager.isConnectionValid(connection) ) {
            connection = dbManager.getConnection();
        }

        String daoName = daoType.getSimpleName();
        SqlDao dao = null;

        switch (daoName) {
            case ("BookDao"):
                dao = new SQLiteDaoAuthor(connection, processManager);
                break;
            case ("AuthorDao"):
                dao = new SQLiteDaoUser(connection, processManager);
                break;
        }
        return daoType.cast(dao);
    }
}