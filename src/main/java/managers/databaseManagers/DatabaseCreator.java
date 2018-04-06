package managers.databaseManagers;

import java.io.IOException;

public interface DatabaseCreator {

    DatabaseManager createDatabase() throws IOException, ClassNotFoundException;
}
