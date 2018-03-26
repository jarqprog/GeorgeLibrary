package controllers;

import dao.BookDao;
import dao.DaoFactory;
import dao.GetableDao;
import enums.DbDriver;
import enums.DbUrl;
import managers.databaseManagers.*;
import models.Book;
import views.RootView;

public class Root {

    private RootView view;
    private DatabaseManager dbManager;
    private DaoFactory daoFactory;

    private Root() {
        view = new RootView();
    }

    public static Root getInstance() {
        return new Root();
    }

    public void runApp() {
        DatabaseConfig dbConfig = SqlConfig.createSQLiteConfiguration(DbUrl.SQLITE, DbDriver.SQLITE);
        dbManager = SQLManager.getSQLiteManager(dbConfig);
        daoFactory = DaoFactory.getInstance(dbManager, SQLProcessManager.getInstance());

        view.displayMessage("Database connection opened, program ready to use.");

        GetableDao<Book> bookDao = daoFactory.getDAO(BookDao.class);
        Book myBook = bookDao.getModelById(1);
        view.displayMessage(myBook.toString());


        initializeController();
        dbManager.closeConnection();
        view.displayMessage("Database connection closed, bye!");
    }

//    private void setupDatabase() {
//        setupSqliteDb();
//    }

//    private void setupSqliteDb() {
//        DatabaseConfig dbConfig = SqlConfig.createSQLiteConfiguration(DbUrl.SQLITE, DbDriver.SQLITE);
//        dbManager = SQLManager.getSQLiteManager(dbConfig);
//        daoFactory = DaoFactory.getInstance(dbManager, SQLProcessManager.getInstance());
//    }

    private void initializeController() {

//        IMainController mainController = MainController.getInstance(new MainView(),
//                DaoFactory.getDAO(TrainerDAO.class),
//                DaoFactory.getDAO(PokemonDAO.class),
//                new PokemonAreaCalc());
//        mainController.executeProgram();
    }
}
