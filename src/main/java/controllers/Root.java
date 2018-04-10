package controllers;

import factory.ModelFactoryManufacture;
import factory.IDaoFactory;
import factory.IModelFactoryManufacture;
import models.book.BookDao;
import dao.DaoFactory;
import dao.GetableDao;
import enums.DbDriver;
import enums.DbFilePath;
import enums.DbUrl;
import managers.databaseManagers.*;
import models.book.FakeBook;
import models.library.ILibrary;
import models.library.LibraryFactory;
import views.ILibraryView;
import views.LibraryView;
import views.RootView;

import java.io.IOException;

public class Root {

    private RootView view;
    private DatabaseManager dbManager;
    private IDaoFactory daoFactory;

    private Root() {
        view = new RootView();
        dbManager = createDatabaseManager();
        daoFactory = DaoFactory.getInstance(dbManager, SQLProcessManager.getInstance());
    }

    public static Root getInstance() {
        return new Root();
    }

    public void runApp() {

        GetableDao<FakeBook> bookDao = daoFactory.getDAO(BookDao.class);
        FakeBook myBook = bookDao.getModelById(1);
        view.displayMessage(myBook.toString());


        initializeController();
        dbManager.closeConnection();
        view.displayMessage("Database connection closed, bye!");
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

    private void initializeController() {

        IModelFactoryManufacture modelFactory = new ModelFactoryManufacture();
        ILibrary library = modelFactory.create(LibraryFactory.class).build();
        ILibraryView view = new LibraryView();
        ILibraryController controller = LibraryController
                .getInstance(view, library, daoFactory, modelFactory);

    }
}
