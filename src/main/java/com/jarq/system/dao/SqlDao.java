package com.jarq.system.dao;

import com.jarq.system.enums.DbLabels;
import com.jarq.system.enums.DbTables;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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

    protected String[] getCurrentValuesCollectionFromGivenLabel(DbTables databaseTable,
                                                                DbLabels databaseLabel)
            throws SQLException {

        // return table with values collection from label of given database table
        int idIndex = 0;
        String[] currentIds = new String[0];
        String query = String.format("Select %s from %s", databaseLabel.getLabel(), databaseTable.getTable());

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String[]> currentIdsCollection = processManager.getObjectsDataCollection(resultSet);

        if (currentIdsCollection != null) {
            currentIds = new String[currentIdsCollection.size()];
            for (int i = 0; i < currentIdsCollection.size(); i++) {

                currentIds[i] = currentIdsCollection.get(i)[idIndex];
            }
        }
        return currentIds;
    }

    protected String findNewValueInCollection(String[] oldValuesCollection,
                                              String[] newValuesCollection)
        throws DaoFailure {

        List<String> oldValues = Arrays.asList(oldValuesCollection);

        for(String value : newValuesCollection) {
            // compare collections: old collection doesn't contain value:
            if(! oldValues.contains(value) ) {
                return value;
            }
        }
        throw new DaoFailure("Value not found!");
    }
}
