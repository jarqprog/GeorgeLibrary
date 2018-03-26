package dao;

import managers.databaseManagers.DatabaseManager;
import managers.databaseManagers.JDBCProcessManager;
import models.Model;

import java.sql.Connection;

public class DaoFactory {

    private DatabaseManager dbManager;
    private JDBCProcessManager processManager;
    private Connection connection;

    public static DaoFactory getInstance(DatabaseManager dbManager, JDBCProcessManager processManager) {
        return new DaoFactory(dbManager, processManager);
    }

    private DaoFactory(DatabaseManager dbManager, JDBCProcessManager processManager) {
        this.dbManager = dbManager;
        this.processManager = processManager;
        connection = dbManager.getConnection();
    }

    public <M extends Model, T extends GetableDao<M>> T getDAO(Class<T> daoType) {

        if( ! dbManager.isConnectionValid(connection) ) {
            connection = dbManager.getConnection();
        }

        String daoName = daoType.getSimpleName();
        GetableDao dao = null;

        switch (daoName) {
            case ("BookDao"):
                dao = new BookDao(connection, processManager);
                break;
//            case ("TrainerDAO"):
//                dao = new TrainerDAO(connection);
//                break;
        }
        return daoType.cast(dao);
    }
}