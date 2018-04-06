package managers.databaseManagers;

import enums.DbDriver;
import enums.DbFilePath;
import enums.DbUrl;

public class SQLConfig implements DatabaseConfig {

    private final String DRIVER;
    private final String URL;
    private final String FILEPATH;

    // get configuration object via static method - different for varied database engines
    public static SQLConfig createSQLiteConfiguration(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH) {
        return new SQLConfig(URL, DRIVER, FILEPATH);
    }

    private SQLConfig(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH) {
        this.URL = URL.getUrl();
        this.DRIVER = DRIVER.getDriver();
        this.FILEPATH = FILEPATH.getPath();
    }

    public String getURL() {
        return URL;
    }

    public String getDRIVER() {
        return DRIVER;
    }

    public String getFILEPATH() {
        return FILEPATH;
    }
}
