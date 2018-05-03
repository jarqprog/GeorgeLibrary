package com.jarq.terminal;

import com.jarq.IRoot;
import com.jarq.system.enums.*;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.helpers.repositoryPath.RepositoryPath;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.managers.filesManagers.RepositoryManager;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.content.IContent;
import com.jarq.system.models.content.IDaoContent;
import com.jarq.system.models.content.SQLiteDaoContent;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.models.user.IDaoUser;

import com.jarq.system.models.user.SQLiteDaoUser;
import com.jarq.terminal.controllers.IRepositoryController;
import com.jarq.terminal.controllers.RepositoryController;
import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.dao.SqlDaoFactory;
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
        IDateTimer dateTimer = DateTimer.getInstane();

        IDaoFactory daoFactory = SqlDaoFactory.getInstance(databaseManager, jdbcProcessManager, dateTimer);
        IDaoAddress daoAddress = daoFactory.createDAO(SQLiteDaoAddress.class);
        IDaoUser daoUser = daoFactory.createDAO(SQLiteDaoUser.class);
        IDaoText daoText = daoFactory.createDAO(SQLiteDaoText.class);
        IDaoRepository daoRepository = daoFactory.createDAO(SQLiteDaoRepository.class);

        try {
//            repositoryManagerTests();




        } catch (Exception e) {
            e.printStackTrace();
        }

        libraryController.runMenu();
        databaseManager.closeConnection();
    }

    private IRepositoryController createLibraryController() {

        IRepositoryView view = new RepositoryView();
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDateTimer dateTimer = DateTimer.getInstane();
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
                .stream(DbTable.values())
                .map(DbTable::getTable)
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

    private void repositoryManagerTests() throws Exception {

        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY, FileExtension.MD);

        IRepositoryManager repositoryManager = RepositoryManager.getInstance(repositoryPath);

        String path01 = RepositoriesPath.FILES_REPOSITORY.getPath()+"jel.txt";
        String path02 = path01 + "001/1.txt";
        System.out.println(repositoryManager.hasFile(path01));
        System.out.println(repositoryManager.hasFile(path02));


        IContent content = getDaoContent().createContent(1, path02);


        System.out.println(repositoryManager.createFile(content));
        System.out.println(repositoryManager.hasFile(path02));
        System.out.println(repositoryManager.hasFile(path01));

    }

    private IDaoContent getDaoContent() {

        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDateTimer dateTimer = DateTimer.getInstane();
        IDaoFactory daoFactory = SqlDaoFactory.getInstance(databaseManager, processManager, dateTimer);
        return daoFactory.createDAO(SQLiteDaoContent.class);
    }

}
