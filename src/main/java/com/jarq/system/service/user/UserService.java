package com.jarq.system.service.user;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.exceptions.ServiceException;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.policy.IEmailPolicy;
import com.jarq.system.policy.IPasswordPolicy;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;
import com.jarq.system.service.Service;

import java.io.IOException;

public class UserService extends Service implements IUserService {

    private final IDaoUser daoUser;
    private final IEmailPolicy emailPolicy;
    private final IPasswordPolicy passwordPolicy;
    private final String serviceFailure = "something goes wrong with user data operation";
    private final IRepositoryManager repositoryManager;
    // log



    public static IUserService getInstance(IDaoUser daoUser, IEmailPolicy emailPolicy,
                                           IPasswordPolicy passwordPolicy,
                                           IRepositoryManager repositoryManager) {
        return new UserService(daoUser, emailPolicy, passwordPolicy, repositoryManager);
    }

    private UserService(IDaoUser daoUser, IEmailPolicy emailPolicy,
                        IPasswordPolicy passwordPolicy, IRepositoryManager repositoryManager) {
        this.daoUser = daoUser;
        this.emailPolicy = emailPolicy;
        this.passwordPolicy = passwordPolicy;
        this.repositoryManager = repositoryManager;
    }

    @Override
    public String createUser(String name, String surname, String email)
            throws SecurityException {
        try {
            IUser user = daoUser.createUser(name, surname, email);
            repositoryManager.createDir(user);
            return user.toString(); // todo

        } catch (DaoFailure | IOException ex) {
            ex.printStackTrace();
            // log
            return serviceFailure;
        }
    }

    @Override
    public String changeUserName(int userId, String name) {

        try {
            IUser user = daoUser.importUser(userId);
            user.setName(name);
            return updateUser(user); // todo

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return serviceFailure;
        }
    }

    @Override
    public String changeUserSurname(int userId, String surname) {
        try {
            IUser user = daoUser.importUser(userId);
            user.setSurname(surname);
            return updateUser(user); // todo

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return serviceFailure;
        }
    }

    @Override
    public String changeUserEmail(int userId, String email) throws ServiceException {
        if(! emailPolicy.validate(email)) {
            throw new ServiceException("wrong email");
        }
        try {
            IUser user = daoUser.importUser(userId);
            user.setEmail(email);
            return updateUser(user); // todo

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return serviceFailure;
        }
    }

    @Override
    public String changeUserPassword(int userId, String password) throws ServiceException {
        if(! passwordPolicy.validate(password)) {
            throw new ServiceException("wrong password");
        }
        try {
            IUser user = daoUser.importUser(userId);
            user.setPassword(password);
            return updateUser(user); // todo

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return serviceFailure;
        }
    }

    @Override
    public String removeUser(int userId) {
        try {
            IUser user = daoUser.importUser(userId);
            boolean isRemovedUserDir = repositoryManager.removeUserRepositories(user);
            if ( daoUser.removeUser(user) && isRemovedUserDir) {
                return user.toString(); // todo
            }
            // log
            return serviceFailure;

        } catch (DaoFailure | IOException ex) {
            ex.printStackTrace();
            // log
            return serviceFailure;
        }
    }


    private String updateUser(IUser user) throws DaoFailure {
        if ( daoUser.updateUser(user) ) {
            return user.toString();
        }
        // log
        return serviceFailure;
    }


}
