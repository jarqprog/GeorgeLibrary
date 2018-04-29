package com.jarq.system.databaseManagers;

import com.jarq.system.exceptions.DatabaseCreationFailure;

public interface DatabaseCreator {

    void createDatabase() throws DatabaseCreationFailure;
}
