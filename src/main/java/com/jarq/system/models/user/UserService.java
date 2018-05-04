package com.jarq.system.models.user;

import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.text.ITextService;

public class UserService implements IUserService {

    private final IDaoUser daoUser;
    private final IDaoRepository daoRepository;
    private final ITextService textService;


    public static IUserService getInstance(IDaoUser daoUser, IDaoRepository daoRepository,
                                           ITextService textService) {
        return new UserService(daoUser, daoRepository, textService);
    }

    private UserService(IDaoUser daoUser, IDaoRepository daoRepository,
                        ITextService textService) {
        this.daoUser = daoUser;
        this.daoRepository = daoRepository;
        this.textService = textService;
    }

    @Override
    public IUser createUser() {
        return null;
    }
}
