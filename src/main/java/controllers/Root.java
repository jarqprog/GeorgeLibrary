package controllers;

import dao.BookDao;
import dao.DaoFactory;
import dao.GetableDao;
import enums.DbDriver;
import enums.DbFilePath;
import enums.DbUrl;
import managers.databaseManagers.*;
import models.FakeBook;
import views.RootView;

import java.io.IOException;

public class Root {

    private RootView view;
    private DatabaseManager dbManager;
    private DaoFactory daoFactory;

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
                try {
                    final DbFilePath dbSetupScript = DbFilePath.DB_SETUP_SCRIPT;
                    DatabaseCreator databaseCreator = SQLiteDbCreator
                            .getInstance(dbConfig, dbSetupScript);
                    manager = databaseCreator.createDatabase();
                } catch (IOException | ClassNotFoundException e) {
                    view.displayMessage("Can't create database, please contact with help desk.");
                    System.exit(0);
                }
            }
            view.displayMessage("Can't run application without database - closing the program..");
            System.exit(0);
        }
        return manager;
    }

    private void initializeController() {

//        IMainController mainController = MainController.getInstance(new MainView(),
//                DaoFactory.getDAO(TrainerDAO.class),
//                DaoFactory.getDAO(PokemonDAO.class),
//                new PokemonAreaCalc());
//        mainController.executeProgram();
    }
}
