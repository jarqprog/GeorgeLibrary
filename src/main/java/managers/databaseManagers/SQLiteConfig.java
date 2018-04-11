package managers.databaseManagers;

import enums.DbDriver;
import enums.DbFilePath;
import enums.DbName;
import enums.DbUrl;

public class SQLConfig implements DatabaseConfig {

    private final String DRIVER;
    private final String URL;
    private final String FILEPATH;
    private final String DB_NAME;

    // get configuration object via static method - different for varied database engines
    public static SQLConfig createSQLiteConfiguration(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH, DbName DB_NAME) {
        return new SQLConfig(URL, DRIVER, FILEPATH, DB_NAME);
    }

    private SQLConfig(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH, DbName DB_NAME) {
        this.URL = URL.getUrl();
        this.DRIVER = DRIVER.getDriver();
        this.FILEPATH = FILEPATH.getPath();
        this.DB_NAME = DB_NAME.getName();
    }

    public String getUrl() {
        return URL;
    }

    public String getDriver() {
        return DRIVER;
    }

    public String getFilepath() {
        return FILEPATH;
    }

    public String getDbName() {
        return DB_NAME;
    }
}
