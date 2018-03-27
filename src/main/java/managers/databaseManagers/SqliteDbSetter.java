package managers.databaseManagers;

import enums.DbFilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqliteDbSetter implements DatabaseSetter {

    private DatabaseManager databaseManager;
    private DbFilePath dbFilePath;
    private DbFilePath dbSetupScript;

    public static DatabaseSetter getInstance(DatabaseManager databaseManager, DbFilePath dbFilePath,
                                             DbFilePath dbSetupScript) {
        return new SqliteDbSetter(databaseManager, dbFilePath, dbSetupScript);
    }

    private SqliteDbSetter(DatabaseManager databaseManager, DbFilePath dbFilePath, DbFilePath dbSetupScript) {
        this.databaseManager = databaseManager;
        this.dbFilePath = dbFilePath;
        this.dbSetupScript = dbSetupScript;
    }

    @Override
    public void prepareDatabase() throws IOException {
        if(! checkIfDatabaseExists()){
            File f = new File(dbFilePath.getPath());
            f.createNewFile();
            updateDatabaseWithSqlFile();
        }
    }


    private Boolean checkIfDatabaseExists(){
        return new File(dbFilePath.getPath()).exists();
    }

    private void clearFile() throws FileNotFoundException {
        new FileOutputStream(dbFilePath.getPath());
    }

    private void updateDatabaseWithSqlFile() throws FileNotFoundException {
        Connection connection = databaseManager.getConnection();
        String delimiter = ";";
        Scanner scanner;
        File sqlFile = new File(dbSetupScript.getPath());
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
