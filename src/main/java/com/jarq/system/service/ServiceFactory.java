package com.jarq.system.service;

import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.address.SQLiteDaoAddress;
import com.jarq.system.models.content.SQLiteDaoContent;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.policy.IAddressPolicy;
import com.jarq.system.policy.IEmailPolicy;
import com.jarq.system.policy.IPasswordPolicy;
import com.jarq.system.service.address.AddressService;
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



    public static IServiceFactory getInstance(IDaoFactory daoFactory,
                                              IRepositoryManager repositoryManager,
                                              IContentReader<String> contentReader,
                                              IContentWriter<String> contentWriter,
                                              IRepositoryPath repositoryPath,
                                              IDateTimer dateTimer,
                                              IEmailPolicy emailPolicy,
                                              IPasswordPolicy passwordPolicy,
                                              IAddressPolicy addressPolicy) {
        return new ServiceFactory(daoFactory, repositoryManager,
                contentReader, contentWriter, repositoryPath, dateTimer,
                emailPolicy, passwordPolicy, addressPolicy);
    }

    private ServiceFactory(IDaoFactory daoFactory,
                           IRepositoryManager repositoryManager,
                           IContentReader<String> contentReader,
                           IContentWriter<String> contentWriter,
                           IRepositoryPath repositoryPath,
                           IDateTimer dateTimer,
                           IEmailPolicy emailPolicy,
                           IPasswordPolicy passwordPolicy,
                           IAddressPolicy addressPolicy) {
        this.daoFactory = daoFactory;
        this.repositoryManager = repositoryManager;
        this.contentReader = contentReader;
        this.contentWriter = contentWriter;
        this.repositoryPath = repositoryPath;
        this.dateTimer = dateTimer;
        this.emailPolicy = emailPolicy;
        this.passwordPolicy = passwordPolicy;
        this.addressPolicy = addressPolicy;
    }

    @Override
    public <T extends IService> T createSQLiteService(Class<T> serviceType) {

        String serviceName = serviceType.getSimpleName();
        IService service = null;

        switch(serviceName) {
            case("TextService"):
                service = TextService.getInstance(
                                repositoryManager,
                                contentReader,
                                contentWriter,
                                daoFactory.createDAO(SQLiteDaoText.class),
                                daoFactory.createDAO(SQLiteDaoContent.class));
                break;
            case("UserService"):
                service = UserService.getInstance(
                                daoFactory.createDAO(SQLiteDaoUser.class),
                                emailPolicy,
                                passwordPolicy);
                break;
            case("RepoService"):
                service = RepoService.getInstance(
                                daoFactory.createDAO(SQLiteDaoRepository.class),
                                daoFactory.createDAO(SQLiteDaoUser.class));
                break;
            case("AddressService"):
                service = AddressService.getInstance(
                            daoFactory.createDAO(SQLiteDaoAddress.class),
                            daoFactory.createDAO(SQLiteDaoUser.class),
                            addressPolicy);
                break;
        }
        return serviceType.cast(service);
    }
}