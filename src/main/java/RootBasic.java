import controllers.IRepositoryController;
import controllers.RepositoryController;
import dao.IDaoFactory;
import dao.SqlDaoFactory;
import enums.DbDriver;
import enums.DbFilePath;
import enums.DbTables;
import enums.DbUrl;
import exceptions.DatabaseCreationFailure;
import managers.databaseManagers.*;
import terminalViews.IRepositoryView;
import terminalViews.RepositoryView;
import terminalViews.RootView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RootBasic implements IClient {

    private final DatabaseConfig databaseConfig;
    private RootView view;
    private IRepositoryController libraryController;
    private DatabaseManager databaseManager;

    private RootBasic() {
        view = new RootView();
        databaseConfig = SQLiteConfig.createSQLiteConfiguration(
                            DbUrl.SQLITE,
                            DbDriver.SQLITE,
                            DbFilePath.SQLITE_DATABASE);
        databaseManager = createSQLiteManager();
        libraryController = createLibraryController();
    }

    public static RootBasic getInstance() {
        return new RootBasic();
    }

    public void runApp() {
        libraryController.runMenu();
    }

    private IRepositoryController createLibraryController() {

        IRepositoryView view = new RepositoryView();
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDaoFactory daoFactory = SqlDaoFactory.getInstance(databaseManager, processManager);

        return RepositoryController.getInstance(view, daoFactory);
    }

    private DatabaseManager createSQLiteManager() {

        if (! isDatabaseValid() ) {
            try {
                executeSQLiteCreationProcess();
            } catch (DatabaseCreationFailure e) {
                e.printStackTrace();
                view.displayMessage(e.getMessage());
                view.displayMessage("Can't run application without database - closing the program..");
                System.exit(0);
            }
        }
        return SQLiteManager.getSQLiteManager(databaseConfig);
    }

    private boolean isDatabaseValid() {

        List<String> databaseTablesToCheck = Arrays
                .stream(DbTables.values())
                .map(DbTables::getTable)
                .collect(Collectors.toList());

        DatabaseValidator database = SQLiteValidator.getInstance(databaseConfig, databaseTablesToCheck);
        return database.isValid();
    }

    private void executeSQLiteCreationProcess() throws DatabaseCreationFailure {
        String userChoice = view
                .getUserInput("Database problem occurred, create new database? (type 'y' to approve) ")
                .toLowerCase();

        if (userChoice.equals("y") ) {
            SQLiteCreator.getInstance(databaseConfig, DbFilePath.DB_SETUP_SCRIPT).createDatabase();
            System.out.println("tworze baze danych");
        } else {
            throw new DatabaseCreationFailure();
        }
    }
}
