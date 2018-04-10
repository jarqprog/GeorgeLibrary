package controllers;

import factory.ModelFactoryManufacture;
import factory.IDaoFactory;
import factory.IModelFactoryManufacture;
import dao.DaoFactory;
import enums.DbDriver;
import enums.DbFilePath;
import enums.DbUrl;
import managers.databaseManagers.*;
import models.library.ILibrary;
import models.library.LibraryFactory;
import views.ILibraryView;
import views.LibraryView;
import views.RootView;

import java.io.IOException;

public class Root {

    private RootView view;
    private ILibraryController libraryController;

    private Root() {
        view = new RootView();
        libraryController = createLibraryController();
    }

    public static Root getInstance() {
        return new Root();
    }

    public void runApp() {
        libraryController.runMenu();
    }

    private ILibraryController createLibraryController() {
        ILibraryView view = new LibraryView();
        IModelFactoryManufacture modelFactoryManufacture = new ModelFactoryManufacture();

        ILibrary library = modelFactoryManufacture.create(LibraryFactory.class).build();
        DatabaseManager databaseManager = createDatabaseManager();
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDaoFactory daoFactory = DaoFactory.getInstance(databaseManager, processManager);

        return LibraryController.getInstance(view, library, daoFactory, modelFactoryManufacture);
    }

    private DatabaseManager createDatabaseManager() {
        // enums to setup database
        DatabaseManager manager = null;
        final DbUrl url = DbUrl.SQLITE;
        final DbDriver driver = DbDriver.SQLITE;
        final DbFilePath dbFilePath = DbFilePath.SQLITE_DATABASE;

        DatabaseConfig dbConfig = SQLConfig.createSQLiteConfiguration(url, driver, dbFilePath);

        try {
            manager = SQLManager.getSQLiteManager(dbConfig);

        } catch(ClassNotFoundException notUsed) {

            String userChoice = view
                    .getUserInput("Database problem occurred, create new database? (type 'y' to approve) ")
                    .toLowerCase();

            if(userChoice.equals("y")) {
                manager = createDatabaseManagerForRecoveredDatabase(dbConfig);
            }

            view.displayMessage("Can't run application without database - closing the program..");
            System.exit(0);
        }
        return manager;
    }

    private DatabaseManager createDatabaseManagerForRecoveredDatabase(DatabaseConfig dbConfig) {
        // recover database using sql script file
        try {
            final DbFilePath dbSetupSQLScript = DbFilePath.DB_SETUP_SCRIPT;

            DatabaseCreator databaseCreator = SQLiteDbCreator
                    .getInstance(dbConfig, dbSetupSQLScript );

            return databaseCreator.createDatabase();

        } catch (IOException | ClassNotFoundException notUsed) {
            view.displayMessage("Can't create database, please contact with help desk.");
            System.exit(0);
            return null;
        }
    }
}
