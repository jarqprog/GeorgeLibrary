package com.jarq.system.databaseManagers;

import java.sql.ResultSet;
import java.util.List;

public interface JDBCProcessManager {

    String[] getObjectData(ResultSet resultSet);

    List<String[]> getObjectsDataCollection(ResultSet resultSet);
}
