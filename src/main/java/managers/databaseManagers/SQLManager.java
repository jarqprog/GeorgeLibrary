package managers.databaseManagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager implements DatabaseManager {

    private Connection connection;
    private String url;

    public static DatabaseManager getSQLiteManager(DatabaseConfig dbConfig) {
        return new SQLManager(dbConfig);
    }

    private SQLManager(DatabaseConfig dbConfig) {
        String driver = dbConfig.getDRIVER();
        this.url = dbConfig.getURL();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Problem with jdbc driver!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if(connection == null) {
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
            if(connection != null && !connection.isClosed()) {
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
