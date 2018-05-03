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
import com.jarq.system.models.text.IText;
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

        IDateTimer dateTimer = DateTimer.getInstane();

        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY,
                        FileExtension.MD);

        IDaoFactory daoFactory = createDaoFactory();
        IDaoAddress daoAddress = daoFactory.createDAO(SQLiteDaoAddress.class);
        IDaoUser daoUser = daoFactory.createDAO(SQLiteDaoUser.class);
        IDaoText daoText = daoFactory.createDAO(SQLiteDaoText.class);
        IDaoRepository daoRepository = daoFactory.createDAO(SQLiteDaoRepository.class);

        try {

            contentTestingAndManager();



        } catch (Exception e) {
            e.printStackTrace();
        }

        libraryController.runMenu();
        databaseManager.closeConnection();
    }

    private IRepositoryController createLibraryController() {

        IRepositoryView view = new RepositoryView();

        return RepositoryController.getInstance(view, createDaoFactory() );
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

        IDaoFactory daoFactory = createDaoFactory();
        IDateTimer dateTimer = DateTimer.getInstane();
        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY, FileExtension.MD);

        IRepositoryManager repositoryManager = RepositoryManager.getInstance(repositoryPath);

        String path01 = RepositoriesPath.FILES_REPOSITORY
                .getPath()+dateTimer.getCurrentDateTime() + "jel.txt";
        String path02 = path01 + "001/1.txt";
        String creationDate01 = dateTimer.getCurrentDateTime();
        System.out.println(repositoryManager.hasFile(path01));
        System.out.println(repositoryManager.hasFile(path02));

        IText text = daoFactory.createDAO(SQLiteDaoText.class)
                .createText("Ojej",1, 1 );

        IContent content = getDaoContent().createContent(text);

        System.out.println(repositoryManager.createFile(content));
        System.out.println(repositoryManager.hasFile(path02));
        System.out.println(repositoryManager.hasFile(path01));

    }

    private IDaoContent getDaoContent() {
        return createDaoFactory().createDAO(SQLiteDaoContent.class);
    }

    private IDaoText getDaoText() {
        return createDaoFactory().createDAO(SQLiteDaoText.class);
    }

    private IRepositoryPath getRepositoryPath() {

        return RepositoryPath.getInstance(RepositoriesPath.FILES_REPOSITORY,
                FileExtension.MD);
    }

    private IDateTimer getDateTimer() {
        return DateTimer.getInstane();
    }

    private IDaoFactory createDaoFactory() {
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDateTimer dateTimer = DateTimer.getInstane();
        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY,
                        FileExtension.MD);
        return SqlDaoFactory
                .getInstance(databaseManager, processManager, dateTimer, repositoryPath);
    }

    private void contentTestingAndManager() throws Exception {
        IDaoText daoText = getDaoText();
        IRepositoryPath repositoryPath = getRepositoryPath();
        IDateTimer dateTimer = getDateTimer();

        IText text001 = daoText.createText("W pustyni i w puszczy11", 1, 2);

        text001.setModificationDate(dateTimer.getCurrentDateTime());
        IDaoContent daoContent = getDaoContent();
        System.out.println(daoContent.importContent(1));
        IContent content001 = daoContent.createContent(text001);
        System.out.println(content001);

        

    }

}
