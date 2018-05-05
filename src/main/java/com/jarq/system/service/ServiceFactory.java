package com.jarq.system.service;

import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.log.ILog;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.content.IDaoContent;
import com.jarq.system.models.content.SQLiteDaoContent;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.policy.IAddressPolicy;
import com.jarq.system.policy.IEmailPolicy;
import com.jarq.system.policy.IPasswordPolicy;
import com.jarq.system.service.address.AddressService;
import com.jarq.system.service.content.ContentService;
import com.jarq.system.service.repository.RepoService;
import com.jarq.system.service.text.TextService;
import com.jarq.system.models.user.SQLiteDaoUser;
import com.jarq.system.service.user.UserService;

public class ServiceFactory implements IServiceFactory {

    private final IDaoFactory daoFactory;
    private final IRepositoryManager repositoryManager;
    private final IContentReader<String> contentReader;
    private final IContentWriter<String> contentWriter;
    private final IRepositoryPath repositoryPath;
    private final IDateTimer dateTimer;
    private final IEmailPolicy emailPolicy;
    private final IPasswordPolicy passwordPolicy;
    private final IAddressPolicy addressPolicy;
    private final ILog log;



    public static IServiceFactory getInstance(IDaoFactory daoFactory,
                                              IRepositoryManager repositoryManager,
                                              IContentReader<String> contentReader,
                                              IContentWriter<String> contentWriter,
                                              IRepositoryPath repositoryPath,
                                              IDateTimer dateTimer,
                                              IEmailPolicy emailPolicy,
                                              IPasswordPolicy passwordPolicy,
                                              IAddressPolicy addressPolicy,
                                              ILog log) {
        return new ServiceFactory(daoFactory, repositoryManager,
                contentReader, contentWriter, repositoryPath, dateTimer,
                emailPolicy, passwordPolicy, addressPolicy, log);
    }

    private ServiceFactory(IDaoFactory daoFactory,
                           IRepositoryManager repositoryManager,
                           IContentReader<String> contentReader,
                           IContentWriter<String> contentWriter,
                           IRepositoryPath repositoryPath,
                           IDateTimer dateTimer,
                           IEmailPolicy emailPolicy,
                           IPasswordPolicy passwordPolicy,
                           IAddressPolicy addressPolicy,
                           ILog log) {
        this.daoFactory = daoFactory;
        this.repositoryManager = repositoryManager;
        this.contentReader = contentReader;
        this.contentWriter = contentWriter;
        this.repositoryPath = repositoryPath;
        this.dateTimer = dateTimer;
        this.emailPolicy = emailPolicy;
        this.passwordPolicy = passwordPolicy;
        this.addressPolicy = addressPolicy;
        this.log = log;
    }

    @Override
    public <T extends IService> T createSQLiteService(Class<T> serviceType) {

        String serviceName = serviceType.getSimpleName();
        IService service = null;

        switch(serviceName) {
            case("TextService"):
                service = TextService.getInstance(
                            log, repositoryManager,
                            daoFactory.createDAO(SQLiteDaoText.class),
                            daoFactory.createDAO(SQLiteDaoRepository.class),
                            daoFactory.createDAO(SQLiteDaoUser.class),
                            dateTimer);
                break;
            case("UserService"):
                service = UserService.getInstance(
                            log, daoFactory.createDAO(SQLiteDaoUser.class),
                            emailPolicy,
                            passwordPolicy,
                            repositoryManager);
                break;
            case("RepoService"):
                service = RepoService.getInstance(
                            log, daoFactory.createDAO(SQLiteDaoRepository.class),
                            daoFactory.createDAO(SQLiteDaoUser.class),
                            repositoryManager, dateTimer);
                break;
            case("AddressService"):
                service = AddressService.getInstance(
                            log, daoFactory.createDAO(SQLiteDaoAddress.class),
                            daoFactory.createDAO(SQLiteDaoUser.class),
                            addressPolicy);
                break;
            case("ContentService"):
                service = ContentService.getInstance(
                        log, daoFactory.createDAO(SQLiteDaoContent.class),
                        daoFactory.createDAO(SQLiteDaoText.class),
                        daoFactory.createDAO(SQLiteDaoRepository.class),
                        repositoryManager, dateTimer,
                        contentReader, contentWriter,
                        repositoryPath);
        }
        return serviceType.cast(service);
    }
}