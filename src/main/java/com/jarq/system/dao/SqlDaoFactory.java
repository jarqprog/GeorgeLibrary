package com.jarq.system.dao;

import com.jarq.system.enums.DbTables;
import com.jarq.system.helpers.IDateTimer;
import com.jarq.system.managers.databaseManagers.DatabaseManager;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.models.user.SQLiteDaoUser;

import java.sql.Connection;

public class SqlDaoFactory implements IDaoFactory {

    private final DatabaseManager dbManager;
    private final JDBCProcessManager processManager;
    private Connection connection;
    private final IDateTimer dateTimer;

    public static IDaoFactory getInstance(DatabaseManager dbManager,
                                          JDBCProcessManager processManager,
                                          IDateTimer dateTimer) {

        return new SqlDaoFactory(dbManager, processManager, dateTimer);
    }

    private SqlDaoFactory(DatabaseManager dbManager,
                          JDBCProcessManager processManager,
                          IDateTimer dateTimer) {

        this.dbManager = dbManager;
        this.processManager = processManager;
        connection = dbManager.getConnection();
        this.dateTimer = dateTimer;
    }

    public <T extends Dao> T createDAO(Class<T> daoType) {

        if( ! dbManager.isConnectionValid(connection) ) {
            connection = dbManager.getConnection();
        }

        String daoName = daoType.getSimpleName();
        SqlDao dao = null;

        switch(daoName) {
            case("SQLiteDaoText"):
                dao = new SQLiteDaoText(connection, processManager, DbTables.TEXTS, dateTimer);
                break;
            case("SQLiteDaoAddress"):
                dao = new SQLiteDaoAddress(connection, processManager, DbTables.ADDRESSES);
                break;
            case("SQLiteDaoUser"):
                dao = new SQLiteDaoUser(connection, processManager,
                        createDAO(SQLiteDaoAddress.class),
                        createDAO(SQLiteDaoRepository.class),
                        DbTables.USERS);
                break;
            case("SQLiteDaoRepository"):
                dao = new SQLiteDaoRepository(connection, processManager,
                        createDAO(SQLiteDaoText.class), DbTables.REPOSITORIES,
                        dateTimer);
                break;
        }
        return daoType.cast(dao);
    }
}