package com.jarq.system.managers.databaseManagers;

import com.jarq.system.enums.DbDriver;
import com.jarq.system.enums.DbFilePath;
import com.jarq.system.enums.DbUrl;

public class SQLiteConfig implements DatabaseConfig {

    private final String DRIVER;
    private final String URL;
    private final String FILEPATH;

    // get configuration object via static method - different for varied database engines
    public static SQLiteConfig createSQLiteConfiguration(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH) {
        return new SQLiteConfig(URL, DRIVER, FILEPATH);
    }

    private SQLiteConfig(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH) {
        this.URL = URL.getUrl();
        this.DRIVER = DRIVER.getDriver();
        this.FILEPATH = FILEPATH.getPath();
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
}
