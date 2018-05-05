package com.jarq.system.service.repository;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.log.ILog;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;
import com.jarq.system.service.Service;

import java.io.IOException;
import java.util.List;

public class RepoService extends Service implements IRepoService {

    private final IDaoRepository daoRepository;
    private final IDaoUser daoUser;
    private final IRepositoryManager repositoryManager;
    private final String serviceFailure = "something goes wrong with repository operation..";

    public static IRepoService getInstance( ILog log,
                                            IDaoRepository daoRepository,
                                            IDaoUser daoUser,
                                            IRepositoryManager repositoryManager) {

        return new RepoService(log, daoRepository, daoUser, repositoryManager);
    }


    private RepoService(ILog log,
                        IDaoRepository daoRepository,
                        IDaoUser daoUser,
                        IRepositoryManager repositoryManager) {
        super(log);
        this.daoRepository = daoRepository;
        this.daoUser = daoUser;
        this.repositoryManager = repositoryManager;
    }

    @Override
    public String createRepository(int userId, String repositoryName) {
        try {
            IUser user = daoUser.importUser(userId);
            IRepository repository = daoRepository.createRepository(user, repositoryName);
            repositoryManager.createDir(repository);
            return repository.toString(); // todo

        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    @Override
    public String changeRepositoryName(int repositoryId, String repositoryName) {
        try {
            IRepository repository = daoRepository.importRepository(repositoryId);
            repository.setName(repositoryName);
            return updateRepository(repository);

        } catch (DaoFailure daoFailure) {
            String message = String.format("%s Problem occurred while changing repository (id:%s) name (%s).",
                    serviceFailure, repositoryId, repositoryName);
            report(message);
            return message;
        }
    }

    @Override
    public String[] getUserRepositories(int userId) {
        try {
            List<IRepository> repositories = daoRepository.importRepositoriesByUserId(userId);
            return repositories.stream()
                    .map(IRepository::toString)
                    .toArray(String[]::new);

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return new String[0];
        }
    }

    @Override
    public String removeRepository(int repositoryId) {
        try {
            IRepository repository = daoRepository.importRepository(repositoryId);

            boolean dbCleared = daoRepository.removeRepository(repository);
            boolean repoCleared = repositoryManager.removeRepository(repository);
            if ( dbCleared && repoCleared ) {
                return repository.toString(); // todo
            }

            // failure:
            String message = String.format("%s problem occurred while removing repository (id:%s). ",
                    serviceFailure, repositoryId);
            report(message);
            return message;

        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    @Override
    public String[] removeUserRepositories(int userId) {
        String[] output = new String[0];
        try {
            List<IRepository> repositories = daoRepository.importRepositoriesByUserId(userId);
            if ( repositories.size() == 0 ) {
                return output; // todo
            }

            // clearing database & repo (validating at the same time)
            boolean dbFailure = ! daoRepository.removeRepositoriesByUserId(userId);
            boolean repoFailure = ! repositoryManager
                    .removeUserRepositories(daoUser.importUser(userId));

            if ( dbFailure | repoFailure ) {
                String message = String.format("Problem occurred while removing " +
                        "users (id:%s) repo (no sql exception)", userId);
                report(message);
                return output;
            }
            return repositories.stream().map(IRepository::toString).toArray(String[]::new);

        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return output;
        }
    }

    private String updateRepository(IRepository repository) throws DaoFailure {
        if ( daoRepository.updateRepository(repository) ) {
            return repository.toString();
        }
        String message = serviceFailure + String
                .format("Problem occurred while updating user (id:%s) repository (id:%s)",
                        repository.getUserId(), repository.getId());
        report(message);
        return serviceFailure;
    }
}
