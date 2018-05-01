package com.jarq.system.managers.databaseManagers;

import com.jarq.system.enums.DbFilePath;
import com.jarq.system.exceptions.DatabaseCreationFailure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLiteCreator implements DatabaseCreator {

    private DatabaseConfig databaseConfig;
    private String setupScript;

    public static DatabaseCreator getInstance(DatabaseConfig databaseConfig, DbFilePath setupScript) {
        return new SQLiteCreator(databaseConfig, setupScript);
    }

    private SQLiteCreator(DatabaseConfig databaseConfig, DbFilePath setupScript) {
        this.databaseConfig = databaseConfig;
        this.setupScript = setupScript.getPath();
    }

    public void createDatabase() throws DatabaseCreationFailure {
            createDatabaseFile();
            updateDatabaseWithSqlFile();
    }

    private void createDatabaseFile() throws DatabaseCreationFailure {
        try {
            File f = new File(databaseConfig.getFilepath());
            f.createNewFile();
        } catch (IOException notUsed) {
            throw new DatabaseCreationFailure();
        }
    }

    private void updateDatabaseWithSqlFile() throws DatabaseCreationFailure {
        String url = databaseConfig.getUrl();
        String delimiter = ";";
        Scanner scanner;
        File sqlFile = new File(setupScript);
        try (   Connection connection = DriverManager.getConnection(url);
                Statement currentStatement = connection.createStatement()   ) {
            scanner = new Scanner(sqlFile).useDelimiter(delimiter);
            connection.setAutoCommit(false);
            while(scanner.hasNext()) {
                // build transaction
                String rawStatement = scanner.next().trim();  // trim() to avoid junk data
                if(rawStatement.length() > 2) {
                    currentStatement.addBatch(rawStatement);
                }
            }
            if(currentStatement != null) {
                // finalize transaction
                currentStatement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException | FileNotFoundException notUsed) {
            notUsed.printStackTrace();
            throw new DatabaseCreationFailure();
        }
    }
}
