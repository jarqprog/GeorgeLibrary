package com.jarq.system.managers.databaseManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface JDBCProcessManager {

    String[] getObjectData(PreparedStatement preparedStatement);

    List<String[]> getObjectsDataCollection(PreparedStatement preparedStatement);

    boolean executeBatch(PreparedStatement preparedStatement, Connection connection);

    boolean executeStatement(PreparedStatement preparedStatement);
}
