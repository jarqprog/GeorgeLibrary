package managers.databaseManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager implements DatabaseManager {

    private Connection connection;
    private String url;

    public static DatabaseManager getSQLiteManager(DatabaseConfig dbConfig) throws ClassNotFoundException {
        return new SQLManager(dbConfig);
    }

    private SQLManager(DatabaseConfig dbConfig) throws ClassNotFoundException {
        String driver = dbConfig.getDRIVER();
        this.url = dbConfig.getURL();
        Class.forName(driver);
    }

    @Override
    public Connection getConnection() {
        try {
            if( isConnectionValid(connection) ) {
                connection = DriverManager.getConnection(url);
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
