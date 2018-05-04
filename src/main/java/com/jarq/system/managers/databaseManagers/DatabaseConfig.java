package com.jarq.system.managers.databaseManagers;

import java.util.Properties;

public interface DatabaseConfig {

    String getUrl();
    String getDriver();
    String getFilepath();
    Properties getProperties();
}
