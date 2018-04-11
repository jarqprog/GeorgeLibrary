package managers.databaseManagers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteValidator implements DatabaseValidator {

    private final String url;
    private final String driver;
    private List<String> tablesToCheck;

    public static DatabaseValidator getInstance(DatabaseConfig databaseConfig, List<String> tablesToCheck) {

        return new SQLiteValidator(databaseConfig, tablesToCheck);
    }

    private SQLiteValidator(DatabaseConfig databaseConfig, List<String> tablesToCheck) {
        this.url = databaseConfig.getUrl();
        this.driver = databaseConfig.getDriver();
        this.tablesToCheck = tablesToCheck;
    }

    @Override
    public boolean isValid() {

        List<String> actualTables = new ArrayList<>();

        try {
            Class.forName(driver);

            try (   Connection connection = DriverManager.getConnection(url);
                    ResultSet resultSet = connection.getMetaData()
                            .getTables(null, null, "%", null) ) {

                while (resultSet.next()) {
                    actualTables.add(resultSet.getString(3));
                }
                resultSet.close();
            }

        } catch (ClassNotFoundException | SQLException notUsed) {
            notUsed.printStackTrace();
        }
        return actualTables.containsAll(tablesToCheck);
    }
}

