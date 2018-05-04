package com.jarq.system.managers.databaseManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface JDBCProcessManager {

    String[] getObjectData(PreparedStatement preparedStatement) throws SQLException;

    List<String[]> getObjectsDataCollection(PreparedStatement preparedStatement) throws SQLException;

    boolean executeBatch(PreparedStatement preparedStatement, Connection connection) throws SQLException;

    boolean executeStatement(PreparedStatement preparedStatement) throws SQLException;
}
