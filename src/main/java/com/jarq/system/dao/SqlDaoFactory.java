package com.jarq.system.dao;

import com.jarq.system.databaseManagers.DatabaseManager;
import com.jarq.system.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.user.SQLiteDaoUser;

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

        switch(daoName) {
            case("SQLiteDaoAddress"):
                dao = new SQLiteDaoAddress(connection, processManager);
                break;
            case("SQLiteDaoUser"):
                dao = new SQLiteDaoUser(connection, processManager,
                        createDAO(SQLiteDaoAddress.class));
                break;
        }
        return daoType.cast(dao);
    }
}