package com.jarq.system.dao;

import com.jarq.system.enums.DbTables;
import com.jarq.system.managers.databaseManagers.DatabaseManager;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.SQLiteDaoText;
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
            case("SQLiteDaoText"):
                dao = new SQLiteDaoText(connection, processManager, DbTables.TEXTS);
                break;
            case("SQLiteDaoAddress"):
                dao = new SQLiteDaoAddress(connection, processManager, DbTables.ADDRESSES);
                break;
            case("SQLiteDaoUser"):
                dao = new SQLiteDaoUser(connection, processManager,
                        createDAO(SQLiteDaoAddress.class), DbTables.USERS);
                break;
            case("SQLiteDaoRepository"):
                dao = new SQLiteDaoRepository(connection, processManager,
                        createDAO(SQLiteDaoText.class), DbTables.REPOSITORIES);
                break;
        }
        return daoType.cast(dao);
    }
}