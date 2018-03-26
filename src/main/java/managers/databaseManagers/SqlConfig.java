package managers.databaseManagers;

import enums.DbDriver;
import enums.DbUrl;

public class SqlConfig implements DatabaseConfig {

    private final String DRIVER;
    private final String URL;

    // get configuration object via static method - different for varied database engines
    public static SqlConfig createSQLiteConfiguration(DbUrl URL, DbDriver DRIVER) {
        return new SqlConfig(URL, DRIVER);
    }

    private SqlConfig(DbUrl URL, DbDriver DRIVER) {
        this.URL = URL.getUrl();
        this.DRIVER = DRIVER.getDriver();
    }

    public String getURL() {
        return URL;
    }

    public String getDRIVER() {
        return DRIVER;
    }
}
