package com.jarq.system.service.user;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.exceptions.ServiceException;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.policy.IEmailPolicy;
import com.jarq.system.policy.IPasswordPolicy;
import com.jarq.system.service.text.ITextService;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;

public class UserService implements IUserService {

    private final IDaoUser daoUser;
    private final IDaoAddress daoAddress;
    private final IDaoRepository daoRepository;
    private final ITextService textService;
    private final IEmailPolicy emailPolicy;
    private final IPasswordPolicy passwordPolicy;



    public static IUserService getInstance(IDaoUser daoUser, IDaoRepository daoRepository,
                                           IDaoAddress daoAddress, ITextService textService,
                                           IEmailPolicy emailPolicy, IPasswordPolicy passwordPolicy) {
        return new UserService(daoUser, daoRepository, daoAddress, textService,
                emailPolicy, passwordPolicy);
    }

    private UserService(IDaoUser daoUser, IDaoRepository daoRepository,
                        IDaoAddress daoAddress, ITextService textService,
                        IEmailPolicy emailPolicy, IPasswordPolicy passwordPolicy) {
        this.daoUser = daoUser;
        this.daoRepository = daoRepository;
        this.daoAddress = daoAddress;
        this.textService = textService;
        this.emailPolicy = emailPolicy;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public String createUser(String name, String surname, String email) {
        IUser user;
        try {
            user = daoUser.createUser(name, surname, email);

            return user.toString(); // todo

        } catch (DaoFailure daoFailure) {
            daoFailure.printStackTrace();
            // log
            return daoUser.createNullUser().toString();
        }
    }

    @Override
    public String changeUserName(int userId, String name) {
        return null;
    }

    @Override
    public String changeUserSurname(int userId, String surname) {
        return null;
    }

    @Override
    public String changeUserEmail(int userId, String email) throws ServiceException {
        return null;
    }

    @Override
    public String changeUserPassword(int userId, String password) throws ServiceException {
        return null;
    }


}
