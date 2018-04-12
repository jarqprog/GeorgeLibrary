package models.worker;

import dao.IDao;
import managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.util.List;

public class WorkerDao implements IDao<Worker> {

    private Connection connection;
    private JDBCProcessManager processManager;

    public WorkerDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
    }

    @Override
    public Worker getModelById(String id) {
        return null;
    }

    @Override
    public List<Worker> getAllModels() {
        return null;
    }
}
