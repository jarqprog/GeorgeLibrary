package com.jarq.system.managers.databaseManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLiteManager implements DatabaseManager {

    private final String url;
    private Connection connection;
    private final Properties properties;

    public static DatabaseManager getSQLiteManager(DatabaseConfig dbConfig) {
        return new SQLiteManager(dbConfig);
    }

    private SQLiteManager(DatabaseConfig dbConfig) {
        this.url = dbConfig.getUrl();
        this.properties = dbConfig.getProperties();
    }

    @Override
    public Connection getConnection() {
        try {
            if(! isConnectionValid(connection) ) {
                connection = DriverManager.getConnection(url, properties);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBC connection problem occurred!");
            System.exit(1);
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            if( isConnectionValid(connection) ) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnectionValid(Connection connection) {
        try {
            return ( connection != null ) && (! connection.isClosed() );
        } catch (SQLException e) {
            return false;
        }
    }
}
