package com.jarq.system.dao;

import com.jarq.system.databaseManagers.JDBCProcessManager;

import java.sql.Connection;

    public abstract class SqlDao implements Dao {

    private Connection connection;
    private JDBCProcessManager processManager;

    public SqlDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected JDBCProcessManager getProcessManager() {
        return processManager;
    }
}
