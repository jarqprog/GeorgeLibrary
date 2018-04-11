package dao;

import factory.IDaoFactory;
import managers.databaseManagers.DatabaseManager;
import managers.databaseManagers.JDBCProcessManager;
import models.Model;
import models.book.BookDao;

import java.sql.Connection;

public class DaoFactory implements IDaoFactory {

    private DatabaseManager dbManager;
    private JDBCProcessManager processManager;
    private Connection connection;

    public static IDaoFactory getInstance(DatabaseManager dbManager, JDBCProcessManager processManager) {
        return new DaoFactory(dbManager, processManager);
    }

    private DaoFactory(DatabaseManager dbManager, JDBCProcessManager processManager) {
        this.dbManager = dbManager;
        this.processManager = processManager;
        connection = dbManager.getConnection();
    }

    public <M extends Model, T extends IDao<M>> T getDAO(Class<T> daoType) {

        if( ! dbManager.isConnectionValid(connection) ) {
            connection = dbManager.getConnection();
        }

        String daoName = daoType.getSimpleName();
        IDao dao = null;

        switch (daoName) {
            case ("BookDao"):
                dao = new BookDao(connection, processManager);
                break;
        }
        return daoType.cast(dao);
    }
}