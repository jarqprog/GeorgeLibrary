package com.jarq.terminal;

import com.jarq.IRoot;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.DateTimer;
import com.jarq.system.helpers.IDateTimer;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.text.SQLiteDaoText;
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
        IDateTimer dateTimer = new DateTimer();

        IDaoFactory daoFactory = SqlDaoFactory.getInstance(databaseManager, jdbcProcessManager, dateTimer);
        IDaoAddress daoAddress = daoFactory.createDAO(SQLiteDaoAddress.class);
        IDaoUser daoUser = daoFactory.createDAO(SQLiteDaoUser.class);
        IDaoText daoText = daoFactory.createDAO(SQLiteDaoText.class);
        IDaoRepository daoRepository = daoFactory.createDAO(SQLiteDaoRepository.class);

        try {

            List<IUser> users = daoUser.importAllUsers();
            System.out.println(users);

            List<IAddress> addresses = daoAddress.importAllAddresses();
            System.out.println(addresses);

            IUser janek = daoUser.importUser(2);
            System.out.println(janek);
            List<IRepository> repos = daoRepository.importRepositoriesByUserId(2);
            System.out.println(repos);

            IUser nn = daoUser.importUser(3);
            System.out.println(nn);
            List<IRepository> nnRepos = daoRepository.importRepositoriesByUserId(3);
            System.out.println(nnRepos);


            // create repo

            IRepository repository = daoRepository.createRepository("Nowe", 3);
            System.out.println(repository);

            // remove repo
//            System.out.println(daoRepository.removeRepositoriesByUserId(3));


//            List<IText> nnTexts = daoText.importTextsByRepositoryId(nnRepos.get(0).getId());
//            System.out.println(nnTexts);

            // creating texts for repo 3
//            IText text001 = daoText.createText("nie wiem co tu npisać", 3);
//            daoText.createText("nie wiem co tu npisać1", 3);
//            daoText.createText("nie wiem co tu npisać2", 3);
//            daoText.createText("nie wiem co tu npisać3", 3);
//            System.out.println(text001);


            // test for importing texts
//            IText text = daoText.importTextWithContent(1);
//            System.out.println(text);
//
//            List<IText> texts = daoText.importTextsByRepositoryId(3);
//            System.out.println(texts);

//            // test for update
//
//            text.setModificationDate("2015-10-10");
//            text.setContent("bla bla bla");
//
//            System.out.println(text);
//            System.out.println(daoText.updateTextWithContent(text));


            // test to remove
            System.out.println(daoText.removeTextsByRepositoryId(3));


        } catch (DaoFailure e) {
            e.printStackTrace();
        }

        libraryController.runMenu();
        databaseManager.closeConnection();
    }

    private IRepositoryController createLibraryController() {

        IRepositoryView view = new RepositoryView();
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDateTimer dateTimer = new DateTimer();
        IDaoFactory daoFactory = SqlDaoFactory.getInstance(databaseManager, processManager, dateTimer);

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
