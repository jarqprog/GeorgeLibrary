package com.jarq.system.service.repository;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;
import com.jarq.system.service.Service;

import java.util.List;

public class RepoService extends Service implements IRepoService {

    private final IDaoRepository daoRepository;
    private final IDaoUser daoUser;
    private final String serviceFailure = "something goes wrong with repository operation..";

    public static IRepoService getInstance(IDaoRepository daoRepository,
                                           IDaoUser daoUser) {

        return new RepoService( daoRepository, daoUser);
    }


    private RepoService(IDaoRepository daoRepository,
                        IDaoUser daoUser) {
        this.daoRepository = daoRepository;
        this.daoUser = daoUser;
    }

    @Override
    public String createRepository(int userId, String repositoryName) {
        try {
            IUser user = daoUser.importUser(userId);
            IRepository repository = daoRepository.createRepository(user, repositoryName);
            return repository.toString(); // todo

        } catch (DaoFailure daoFailure) {
//            daoFailure.printStackTrace();
            // log
            return serviceFailure;
        }
    }

    @Override
    public String changeRepositoryName(int repositoryId, String repositoryName) {
        try {
            IRepository repository = daoRepository.importRepository(repositoryId);
            repository.setName(repositoryName);
            return updateRepository(repository); // todo

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return serviceFailure;
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
            daoFailure.printStackTrace();
            // log
            return new String[0];
        }
    }

    @Override
    public String removeRepository(int repositoryId) {
        try {
            IRepository repository = daoRepository.importRepository(repositoryId);
            if ( daoRepository.removeRepository(repository) ) {
                return repository.toString(); // todo
            }
            // log
            return serviceFailure;

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return serviceFailure;
        }
    }



    private String updateRepository(IRepository repository) throws DaoFailure {
        if ( daoRepository.updateRepository(repository) ) {
            return repository.toString();
        }
        // log
        return serviceFailure;
    }
}
