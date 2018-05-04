package com.jarq.system.service;

import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.content.SQLiteDaoContent;
import com.jarq.system.models.repository.SQLiteDaoRepository;
import com.jarq.system.models.text.SQLiteDaoText;
import com.jarq.system.models.text.TextService;
import com.jarq.system.models.user.SQLiteDaoUser;
import com.jarq.system.models.user.UserService;

public class ServiceFactory implements IServiceFactory {

    private final IDaoFactory daoFactory;
    private final IRepositoryManager repositoryManager;
    private final IContentReader<String> contentReader;
    private final IContentWriter<String> contentWriter;

    private ServiceFactory(IDaoFactory daoFactory,
                           IRepositoryManager repositoryManager,
                           IContentReader<String> contentReader,
                           IContentWriter<String> contentWriter) {
        this.daoFactory = daoFactory;
        this.repositoryManager = repositoryManager;
        this.contentReader = contentReader;
        this.contentWriter = contentWriter;
    }

    public static IServiceFactory getInstance(IDaoFactory daoFactory,
                                              IRepositoryManager repositoryManager,
                                              IContentReader<String> contentReader,
                                              IContentWriter<String> contentWriter) {
        return new ServiceFactory(daoFactory, repositoryManager, contentReader, contentWriter);
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
                                daoFactory.createDAO(SQLiteDaoRepository.class),
                                createSQLiteService(TextService.class));
                break;
        }
        return serviceType.cast(service);
    }
}
