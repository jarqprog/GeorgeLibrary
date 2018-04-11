package managers.databaseManagers;

import enums.DbFilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLiteDbCreator implements DatabaseCreator {

    private DatabaseConfig databaseConfig;
    private String setupScript;

    public static DatabaseCreator getInstance(DatabaseConfig databaseConfig, DbFilePath setupScript) {
        return new SQLiteDbCreator(databaseConfig, setupScript);
    }

    private SQLiteDbCreator(DatabaseConfig databaseConfig, DbFilePath setupScript) {
        this.databaseConfig = databaseConfig;
        this.setupScript = setupScript.getPath();
    }

    public DatabaseManager createDatabase() throws IOException, ClassNotFoundException, SQLException {
        DatabaseManager manager = createManager();
        updateDatabaseWithSqlFile(manager);
        return manager;
    }


    private DatabaseManager createManager() throws IOException, ClassNotFoundException, SQLException {
        File f = new File(databaseConfig.getFILEPATH());
        f.createNewFile();  // just to create new file for database
        return SQLManager.getSQLiteManager(databaseConfig);
    }

    private void updateDatabaseWithSqlFile(DatabaseManager databaseManager) throws FileNotFoundException {
        Connection connection = databaseManager.getConnection();
        try {
            boolean isValid = (! connection.isClosed() && ! (connection == null));
            System.out.println(String.valueOf(isValid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delimiter = ";";
        Scanner scanner;
        File sqlFile = new File(setupScript);
        scanner = new Scanner(sqlFile).useDelimiter(delimiter);
        try (Statement currentStatement = connection.createStatement()) {
            connection.setAutoCommit(false);
            while(scanner.hasNext()) {
                // build transaction
                String rawStatement = scanner.next().trim();  // trim() to avoid trash data
                if(rawStatement.length() > 2) {
                    currentStatement.addBatch(rawStatement);
                }
            }
            if(currentStatement != null) {
                // finalize transaction
                currentStatement.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (databaseManager.isConnectionValid(connection)) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        scanner.close();
    }
}
