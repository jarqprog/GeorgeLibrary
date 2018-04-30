package com.jarq.system.managers.databaseManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface JDBCProcessManager {

    String[] getObjectData(ResultSet resultSet);

    List<String[]> getObjectsDataCollection(ResultSet resultSet);

    boolean executeBatch(PreparedStatement preparedStatement, Connection connection);

    boolean executeStatement(PreparedStatement preparedStatement);
}
