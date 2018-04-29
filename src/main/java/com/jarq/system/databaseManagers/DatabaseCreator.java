package com.jarq.databaseManagers;

import com.jarq.exceptions.DatabaseCreationFailure;

public interface DatabaseCreator {

    void createDatabase() throws DatabaseCreationFailure;
}
