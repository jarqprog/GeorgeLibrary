package com.jarq.system.service.user;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.service.text.ITextService;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;

public class UserService implements IUserService {

    private final IDaoUser daoUser;
    private final IDaoAddress daoAddress;
    private final IDaoRepository daoRepository;
    private final ITextService textService;



    public static IUserService getInstance(IDaoUser daoUser, IDaoRepository daoRepository,
                                           IDaoAddress daoAddress, ITextService textService) {
        return new UserService(daoUser, daoRepository, daoAddress, textService);
    }

    private UserService(IDaoUser daoUser, IDaoRepository daoRepository,
                        IDaoAddress daoAddress, ITextService textService) {
        this.daoUser = daoUser;
        this.daoRepository = daoRepository;
        this.daoAddress = daoAddress;
        this.textService = textService;
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
    public boolean createNote(int userId, int repositoryId, String title, String content) {
        return false;
    }
}
