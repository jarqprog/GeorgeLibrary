package factory;

import enums.FilePath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database failure occurred..");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            DbConnection.getInstance().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database failure occurred..");
        }
    }

    private static class DbConnection {
        // singleton

        private final String DB_NAME = FilePath.DATA_BASE.getFilePath();
        private final String URL = "jdbc:sqlite:" + DB_NAME;
        private static Connection connection;
        private static DbConnection instance;

        private DbConnection() {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database connection failure");
            }
        }

        private Connection getConnection() {
            return connection;
        }

        private void closeConnection() throws SQLException {
            if (connection != null) {
                connection.close();
            }
        }

        private static DbConnection getInstance() throws SQLException {
            if (instance == null) {
                instance = new DbConnection();
            } else if (instance.getConnection().isClosed()) {
                instance = new DbConnection();
            }
            return instance;
        }
    }
}
