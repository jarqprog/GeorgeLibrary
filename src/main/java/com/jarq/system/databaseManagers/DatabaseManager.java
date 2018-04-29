package com.jarq.system.databaseManagers;

import java.sql.Connection;

public interface DatabaseManager {

    Connection getConnection();
    void closeConnection();
    boolean isConnectionValid(Connection connection);

}