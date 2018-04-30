package com.jarq.terminal;

import com.jarq.IRoot;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;
import com.jarq.system.models.user.SQLiteDaoUser;
import com.jarq.terminal.controllers.IRepositoryController;
import com.jarq.terminal.controllers.RepositoryController;
import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.dao.SqlDaoFactory;
import com.jarq.system.enums.DbDriver;
import com.jarq.system.enums.DbFilePath;
import com.jarq.system.enums.DbTables;
import com.jarq.system.enums.DbUrl;
import com.jarq.system.exceptions.DatabaseCreationFailure;
import com.jarq.system.managers.databaseManagers.*;
import com.jarq.terminal.views.IRepositoryView;
import com.jarq.terminal.views.RepositoryView;
import com.jarq.terminal.views.RootView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RootTerminal implements IRoot {

    private final DatabaseConfig databaseConfig;
    private RootView view;
    private IRepositoryController libraryController;
    private DatabaseManager databaseManager;

    private RootTerminal() {
        view = new RootView();
        databaseConfig = SQLiteConfig.createSQLiteConfiguration(
                            DbUrl.SQLITE,
                            DbDriver.SQLITE,
                            DbFilePath.SQLITE_DATABASE);
        databaseManager = createSQLiteManager();
        libraryController = createLibraryController();
    }

    public static RootTerminal getInstance() {
        return new RootTerminal();
    }

    public void runApp() {

        // for tests:

        JDBCProcessManager jdbcProcessManager = SQLProcessManager.getInstance();

        IDaoFactory daoFactory = SqlDaoFactory.getInstance(databaseManager, jdbcProcessManager);
        IDaoAddress daoAddress = daoFactory.createDAO(SQLiteDaoAddress.class);


        IDaoUser daoUser = daoFactory.createDAO(SQLiteDaoUser.class);

        IUser fake = daoUser.createNullUser();
        System.out.println(fake);


        try {

            IUser firstUser = daoUser.createUser("Jarek", "Kucharczyk",
                    "jarek@gmail.com", daoAddress.importAddress(1));

            System.out.println(firstUser);

            List<IAddress> addresses = daoAddress.importAllAddresses();
            addresses.forEach(System.out::println);
            System.out.println(addresses);


        } catch (DaoFailure e) {
            e.printStackTrace();
        }

        libraryController.runMenu();
        databaseManager.closeConnection();
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
