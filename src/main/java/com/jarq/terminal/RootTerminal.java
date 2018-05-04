package com.jarq.terminal;

import com.jarq.IRoot;
import com.jarq.system.enums.*;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.helpers.repositoryPath.RepositoryPath;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.managers.filesManagers.RepositoryManager;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.content.IContent;
import com.jarq.system.models.content.IDaoContent;
import com.jarq.system.models.content.SQLiteDaoContent;
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
import com.jarq.system.exceptions.DatabaseCreationFailure;
import com.jarq.system.managers.databaseManagers.*;
import com.jarq.terminal.views.IRepositoryView;
import com.jarq.terminal.views.RepositoryView;
import com.jarq.terminal.views.RootView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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

        try {
//            testDaoRepository();
//            testUserAddress();
//            testDaoText();
            testDaoContent();


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
        IDateTimer dateTimer = DateTimer.getInstance();
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
                .createText(getDaoRepository().importRepository(1), "Ojej");

        IContent content = getDaoContent().createContent(text);

        System.out.println(repositoryManager.createFile(content));
        System.out.println(repositoryManager.hasFile(path02));
        System.out.println(repositoryManager.hasFile(path01));

    }

    private IDaoUser getDaoUser() {
        return createDaoFactory().createDAO(SQLiteDaoUser.class);
    }


    private IDaoContent getDaoContent() {
        return createDaoFactory().createDAO(SQLiteDaoContent.class);
    }

    private IDaoText getDaoText() {
        return createDaoFactory().createDAO(SQLiteDaoText.class);
    }

    private IDaoAddress getDaoAddress() {
        return createDaoFactory().createDAO(SQLiteDaoAddress.class);
    }

    private IDaoRepository getDaoRepository() {
        return createDaoFactory().createDAO(SQLiteDaoRepository.class);
    }


    private IRepositoryPath getRepositoryPath() {

        return RepositoryPath.getInstance(RepositoriesPath.FILES_REPOSITORY,
                FileExtension.MD);
    }

    private IDateTimer getDateTimer() {
        return DateTimer.getInstance();
    }

    private IDaoFactory createDaoFactory() {
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDateTimer dateTimer = DateTimer.getInstance();
        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY,
                        FileExtension.MD);
        return SqlDaoFactory
                .getInstance(databaseManager, processManager, dateTimer, repositoryPath);
    }

    private void contentTestingAndManager() throws Exception {
        IDaoText daoText = getDaoText();
//        IRepositoryPath repositoryPath = getRepositoryPath();
        IDateTimer dateTimer = getDateTimer();

        IText text001 = daoText.importText(13);

        text001.setModificationDate(dateTimer.getCurrentDateTime());
        IDaoContent daoContent = getDaoContent();
        System.out.println(daoContent.importContent(1));

//        for (int i=0; i<20; i++) {
//
//            IContent content001 = daoContent.createContent(text001);
//            System.out.println(content001);
//        }


//        System.out.println(daoContent.removeContentsByTextId(13));
//        System.out.println(daoContent.removeContent(11));
//
//        List<IContent> contents = daoContent.importContentsByTextId(13);
//        for(IContent content : contents) {
//            System.out.println(content);
//        }

    }

    private void testUserAddress() throws Exception {
        IDaoUser daoUser = getDaoUser();
        IDaoAddress daoAddress = getDaoAddress();
//        IUser user = daoUser.createUser("Jan", "Nowak", "notak@gmail.com");
//        System.out.println(daoAddress.importAddressByUser(user));

        System.out.println(daoUser.importUser(100));
        System.out.println(daoUser.importUser(103));
        System.out.println(daoUser.importUser(102));
        System.out.println(daoUser.importUser(101));

    }

    private void testDaoRepository() throws Exception {
        IDaoRepository daoRepository = getDaoRepository();
        IDaoUser daoUser = getDaoUser();
        System.out.println(daoRepository.importRepository(1001));
        System.out.println(daoRepository.importRepository(1003));
        System.out.println(daoRepository.importRepository(1004));
        System.out.println(daoRepository.importRepositoriesByUser(daoUser.createNullUser()));
    }

    private void testDaoText() throws Exception {
        IDaoText daoText = getDaoText();

        System.out.println(daoText.removeTextsByRepositoryId(1000));
        System.out.println(daoText.importTextsByUser(getDaoUser().importUser(3)));
        System.out.println(daoText.removeTextsByRepositoryId(3));
        System.out.println(daoText.removeTextsByRepository(getDaoRepository().importRepository(3)));
        System.out.println(daoText.importTextsByUser(getDaoUser().importUser(3)));
    }

    private void testDaoContent() throws Exception {
        IDaoRepository daoRepository = getDaoRepository();
        IDaoUser daoUser = getDaoUser();
        IDaoContent daoContent = getDaoContent();
        IDaoText daoText = getDaoText();


        int random = getRandomNumber(100);
        String name = getRandomName();
        String surname = getRandomSurname();
        IUser randomUser = daoUser.createUser(name, surname, name + random + surname +"@yahoo.com");
        IRepository repository01 = daoRepository.createRepository(randomUser, name + random);
        IRepository repository02 = daoRepository.createRepository(randomUser, name + random);

        for (int i=0; i<100; i++) {
            random = getRandomNumber(1000);
            IText text01 = daoText.createText(repository01, name+" note" + random);
            IText text02 = daoText.createText(repository02, "new02" + random);
            IContent content001 = daoContent.createContent(text01);
            IContent content002 = daoContent.createContent(text02);
        }
    }




    private int getRandomNumber(int bound) {
        return new Random().nextInt(bound);
    }

    private String getRandomName() {
        String[] names = {"Mark", "Peter", "Tom", "Carl"};
        return names[getRandomNumber(names.length-1)];
    }

    private String getRandomSurname() {
        String[] surnames = {"Novak", "Smith", "Koval", "Black"};
        return surnames[getRandomNumber(surnames.length-1)];
    }
}
