package com.jarq.system.managers.databaseManagers;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class SQLProcessManager implements JDBCProcessManager {

    public static JDBCProcessManager getInstance() {
        return new SQLProcessManager();
    }

    private SQLProcessManager() {}

    public String[] getObjectData(ResultSet resultSet) {
        String[] objectData = new String[0];
        try {
            if (resultSet.isBeforeFirst()) {
                ResultSetMetaData meta = resultSet.getMetaData();
                int colCounter = meta.getColumnCount();
                while (resultSet.next()) {
                    List<String> columnList = new ArrayList<>();
                    for (int column = 1; column <= colCounter; ++column) {
                        Object value = resultSet.getObject(column);
                        columnList.add(String.valueOf(value));
                    }
                    objectData = columnList.toArray(objectData);
                }
                closeResources(resultSet);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResources(resultSet);
        }
        return objectData;
    }

    public List<String[]> getObjectsDataCollection(ResultSet resultSet) {

        List<String[]> objectsDataCollection = new ArrayList<>();

        try {
            if (resultSet.isBeforeFirst()) {
                ResultSetMetaData meta = resultSet.getMetaData();
                int colCounter = meta.getColumnCount();
                while (resultSet.next()) {
                    List<String> columnList = new ArrayList<>();
                    for (int column = 1; column <= colCounter; ++column) {
                        Object value = resultSet.getObject(column);
                        columnList.add(String.valueOf(value));
                    }
                    String[] columnArray = new String[columnList.size()];
                    columnArray = columnList.toArray(columnArray);
                    objectsDataCollection.add(columnArray);
                }
            }
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeResources(resultSet);
        }
        return objectsDataCollection;
    }

    public boolean executeBatch(PreparedStatement preparedStatement, Connection connection) {
        try {
            connection.setAutoCommit(false);
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (preparedStatement != null) {
                closeResources(preparedStatement);
            }
        }
        return true;
    }

    public boolean executeUpdate(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (preparedStatement != null) {
                closeResources(preparedStatement);
            }
        }
        return true;
    }

    private <T extends AutoCloseable> void closeResources(T resources) {
        if(resources != null) {
            try {
                resources.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}