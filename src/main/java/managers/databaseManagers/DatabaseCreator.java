package managers.databaseManagers;

import exceptions.DatabaseCreationFailure;

public interface DatabaseCreator {

    void createDatabase() throws DatabaseCreationFailure;
}
