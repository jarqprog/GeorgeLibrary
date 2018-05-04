package com.jarq.system.managers.databaseManagers;

import com.jarq.system.enums.DbDriver;
import com.jarq.system.enums.DbFilePath;
import com.jarq.system.enums.DbUrl;

import java.util.Properties;

public class SQLiteConfig implements DatabaseConfig {

    private final String DRIVER;
    private final String URL;
    private final String FILEPATH;
    private final Properties PROPERTIES;

    // get configuration object via static method - different for varied database engines
    public static SQLiteConfig createSQLiteConfiguration(DbUrl URL, DbDriver DRIVER,
                                                         DbFilePath FILEPATH, Properties properties) {
        return new SQLiteConfig(URL, DRIVER, FILEPATH, properties);
    }

    public static SQLiteConfig createSQLiteConfiguration(DbUrl URL, DbDriver DRIVER,
                                                         DbFilePath FILEPATH) {
        return new SQLiteConfig(URL, DRIVER, FILEPATH, getSQLiteDefaultProperties());
    }

    private SQLiteConfig(DbUrl URL, DbDriver DRIVER, DbFilePath FILEPATH, Properties properties) {
        this.URL = URL.getUrl();
        this.DRIVER = DRIVER.getDriver();
        this.FILEPATH = FILEPATH.getPath();
        this.PROPERTIES = properties;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public String getDriver() {
        return DRIVER;
    }

    @Override
    public String getFilepath() {
        return FILEPATH;
    }

    @Override
    public Properties getProperties() {
        return PROPERTIES;
    }

    private static Properties getSQLiteDefaultProperties() {
        org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
        config.enforceForeignKeys(true);
        return config.toProperties();
    }
}
