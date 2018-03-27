package controllers;

import dao.BookDao;
import dao.DaoFactory;
import dao.GetableDao;
import enums.DbDriver;
import enums.DbFilePath;
import enums.DbUrl;
import managers.databaseManagers.*;
import models.Book;
import models.FakeBook;
import views.RootView;

import java.io.IOException;

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

        setupDatabase();
        setupObjects();

        GetableDao<FakeBook> bookDao = daoFactory.getDAO(BookDao.class);
        FakeBook myBook = bookDao.getModelById(1);
        view.displayMessage(myBook.toString());


        initializeController();
        dbManager.closeConnection();
        view.displayMessage("Database connection closed, bye!");
    }

    private void setupDatabase() {
        try {

            setupSqliteDb();

        } catch (IOException e) {
            e.printStackTrace();
            view.displayMessage("Database problem occurred.");
            System.exit(-1);
        }
        view.displayMessage("Database connection opened, program ready to use.");
    }

    private void setupSqliteDb() throws IOException {

        // enums to setup database:
        final DbFilePath dbFilePath = DbFilePath.SQLITE_DATABASE;
        final DbFilePath dbSetupScript = DbFilePath.DB_SETUP_SCRIPT;
        final DbUrl url = DbUrl.SQLITE;
        final DbDriver driver = DbDriver.SQLITE;

        DatabaseConfig dbConfig = SQLConfig.createSQLiteConfiguration(url, driver);
        dbManager = SQLManager.getSQLiteManager(dbConfig);
        DatabaseSetter databaseSetter = SqliteDbSetter
                .getInstance(dbManager, dbFilePath, dbSetupScript);
        databaseSetter.prepareDatabase();
    }

    private void setupObjects() {
        daoFactory = DaoFactory.getInstance(dbManager, SQLProcessManager.getInstance());
    }

    private void initializeController() {

//        IMainController mainController = MainController.getInstance(new MainView(),
//                DaoFactory.getDAO(TrainerDAO.class),
//                DaoFactory.getDAO(PokemonDAO.class),
//                new PokemonAreaCalc());
//        mainController.executeProgram();
    }
}
