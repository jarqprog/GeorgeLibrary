package com.jarq.terminal;

import com.jarq.IRoot;
import com.jarq.system.enums.*;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.helpers.repositoryPath.RepositoryPath;
import com.jarq.system.log.ILog;
import com.jarq.system.log.ILogWriter;
import com.jarq.system.log.LogWriter;
import com.jarq.system.log.Logger;
import com.jarq.system.managers.filesManagers.*;
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
import com.jarq.system.policy.*;
import com.jarq.system.service.IServiceFactory;
import com.jarq.system.service.ServiceFactory;
import com.jarq.system.service.address.AddressService;
import com.jarq.system.service.address.IAddressService;
import com.jarq.system.service.repository.IRepoService;
import com.jarq.system.service.repository.RepoService;
import com.jarq.system.service.user.IUserService;
import com.jarq.system.service.user.UserService;
import com.jarq.terminal.controllers.IRepositoryController;
import com.jarq.terminal.controllers.RepositoryController;
import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.dao.SqlDaoFactory;
import com.jarq.system.exceptions.DatabaseCreationFailure;
import com.jarq.system.managers.databaseManagers.*;
import com.jarq.terminal.views.IRepositoryView;
import com.jarq.terminal.views.RepositoryView;
import com.jarq.terminal.views.RootView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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
//            removeTest();
//            repositoryManagerTests();
//            testDaoContent();

//            serviceFactoryTest();

            userServiceTest();

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

    private IDaoFactory createDaoFactory() {
        JDBCProcessManager processManager = SQLProcessManager.getInstance();
        IDateTimer dateTimer = DateTimer.getInstance();
        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY,
                        FileExtension.MD);
        return SqlDaoFactory
                .getInstance(databaseManager, processManager, dateTimer, repositoryPath);
    }

    private IServiceFactory createServiceFactory() {

        IDaoFactory daoFactory = createDaoFactory();
        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY,
                        FileExtension.MD);
        IRepositoryManager repositoryManager = RepositoryManager
                .getInstance(repositoryPath);
        IContentReader<String> contentReader = TextReader
                .getInstance(StandardCharsets.UTF_8);
        IContentWriter<String> contentWriter = TextWriter
                .getInstance(StandardCharsets.UTF_8);

        IDateTimer dateTimer = getDateTimer();
        IEmailPolicy emailPolicy = new EmailPolicy();
        IPasswordPolicy passwordPolicy = new PasswordPolicy();
        IAddressPolicy addressPolicy = new AddressPolicy();
        IDateTimer loggerDateTimer = DateTimer.getInstance(DateTimerFormatter.LOGGER);
        ILogWriter<String> logWriter = LogWriter.getInstance(LogPath.LOGGER);
        ILog log = Logger.getInstance(loggerDateTimer, logWriter);

        return ServiceFactory.getInstance(daoFactory,
                repositoryManager, contentReader, contentWriter,
                repositoryPath, dateTimer, emailPolicy,
                passwordPolicy, addressPolicy, log);
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

    private void populateDbWithTextsAndContents() throws Exception {
        IDaoRepository daoRepository = getDaoRepository();
        IDaoUser daoUser = getDaoUser();
        IDaoContent daoContent = getDaoContent();
        IDaoText daoText = getDaoText();


        int random = getRandomNumber(100);
        String name = getRandomName();
        String surname = getRandomSurname();
        IUser randomUser = daoUser.createUser(name, surname, name + random + surname +"@yahoo.com");
        IRepository repository01 = daoRepository.createRepository(randomUser, name + random);


        for (int i=0; i<100; i++) {
            random = getRandomNumber(1000);
            IText text01 = daoText.createText(repository01, name+" note" + random);
            IContent content001 = daoContent.createContent(text01);
        }

    }

    private void populateDbWithUsers() throws Exception {
        IUserService userService = createServiceFactory().createSQLiteService(UserService.class);

        for (int i=0; i<40; i++) {
            int random = getRandomNumber(40);
            String name = getRandomName();
            String surname = getRandomSurname();
            String randomUser = userService.createUser(name, surname, name + random + surname +"@yahoo.com");
            System.out.println(randomUser);

        }

    }

    /**
     *
     *  TESTS
     */

    private void serviceFactoryTest() {
        IServiceFactory serviceFactory = createServiceFactory();
        IUserService userService = serviceFactory.createSQLiteService(UserService.class);
        IAddressService addressService = serviceFactory.createSQLiteService(AddressService.class);
        IRepoService repoService = serviceFactory.createSQLiteService(RepoService.class);
    }

    private void repositoryManagerTests() throws Exception {

        IDaoFactory daoFactory = createDaoFactory();
        IDateTimer dateTimer = DateTimer.getInstance();
        IRepositoryPath repositoryPath = RepositoryPath
                .getInstance(RepositoriesPath.FILES_REPOSITORY, FileExtension.MD);

        IDaoUser daoUser = daoFactory.createDAO(SQLiteDaoUser.class);

        IRepositoryManager repositoryManager = RepositoryManager.getInstance(repositoryPath);
        String path = repositoryPath.userDir(daoUser.importUser(1));

        System.out.println(path);
        System.out.println(repositoryManager.hasFile(path));

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

        System.out.println(daoText.importTextsByUser(getDaoUser().importUser(3)));

        System.out.println(daoText.importTextsByUser(getDaoUser().importUser(3)));
    }

    private void testDaoContent() throws Exception {
        populateDbWithTextsAndContents();
    }

    private void removeTest() throws Exception {
    }


    private void userServiceTest() throws Exception {
        // create user

        populateDbWithUsers();
    }









}
