package com.jarq.system.dao;

import com.jarq.system.enums.DbTable;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.managers.databaseManagers.DatabaseManager;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.content.SQLiteDaoContent;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.models.user.SQLiteDaoUser;

import java.sql.Connection;

public class SqlDaoFactory implements IDaoFactory {

    private final DatabaseManager dbManager;
    private final JDBCProcessManager processManager;
    private Connection connection;
    private final IDateTimer dateTimer;
    private final IRepositoryPath repositoryPath;

    public static IDaoFactory getInstance(DatabaseManager dbManager,
                                          JDBCProcessManager processManager,
                                          IDateTimer dateTimer,
                                          IRepositoryPath repositoryPath) {

        return new SqlDaoFactory(dbManager, processManager, dateTimer, repositoryPath);
    }

    private SqlDaoFactory(DatabaseManager dbManager,
                          JDBCProcessManager processManager,
                          IDateTimer dateTimer,
                          IRepositoryPath repositoryPath) {

        this.dbManager = dbManager;
        this.processManager = processManager;
        connection = dbManager.getConnection();
        this.dateTimer = dateTimer;
        this.repositoryPath = repositoryPath;
    }

    public <T extends Dao> T createDAO(Class<T> daoType) {

        if( ! dbManager.isConnectionValid(connection) ) {
            connection = dbManager.getConnection();
        }

        String daoName = daoType.getSimpleName();
        SqlDao dao = null;

        switch(daoName) {
            case("SQLiteDaoText"):
                dao = new SQLiteDaoText(connection, processManager, DbTable.TEXTS, dateTimer);
                break;
            case("SQLiteDaoAddress"):
                dao = new SQLiteDaoAddress(connection, processManager, DbTable.ADDRESSES);
                break;
            case("SQLiteDaoUser"):
                dao = new SQLiteDaoUser(connection, processManager,
                        createDAO(SQLiteDaoAddress.class), DbTable.USERS);
                break;
            case("SQLiteDaoRepository"):
                dao = new SQLiteDaoRepository(connection, processManager,
                        DbTable.REPOSITORIES, dateTimer);
                break;
            case("SQLiteDaoContent"):
                dao = new SQLiteDaoContent(connection, processManager, DbTable.CONTENTS,
                        repositoryPath);
                break;
        }
        return daoType.cast(dao);
    }
}