package com.jarq.system.managers.databaseManagers;

import com.jarq.system.exceptions.DatabaseCreationFailure;

public interface DatabaseCreator {

    void createDatabase() throws DatabaseCreationFailure;
}
