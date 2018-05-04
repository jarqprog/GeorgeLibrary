package com.jarq.system.service.repository;

import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.service.Service;

public class RepoService extends Service implements IRepoService {

    private final IDaoRepository daoRepository;
    private final IRepositoryManager repositoryManager;
    private final IRepositoryPath repositoryPath;
    private final IDateTimer dateTimer;

    public static IRepoService getInstance(IDaoRepository daoRepository,
                                           IRepositoryManager repositoryManager,
                                           IRepositoryPath repositoryPath,
                                           IDateTimer dateTimer) {

        return new RepoService( daoRepository, repositoryManager,
                                repositoryPath, dateTimer);
    }


    private RepoService(IDaoRepository daoRepository,
                       IRepositoryManager repositoryManager,
                       IRepositoryPath repositoryPath,
                       IDateTimer dateTimer) {
        this.daoRepository = daoRepository;
        this.repositoryManager = repositoryManager;
        this.repositoryPath = repositoryPath;
        this.dateTimer = dateTimer;
    }

    @Override
    public String createRepository(int userId, String repositoryName) {
        return null;
    }

    @Override
    public String[] getUserRepositories(int userId) {
        return new String[0];
    }

    @Override
    public boolean removeRepository(int repositoryId) {
        return false;
    }
}
