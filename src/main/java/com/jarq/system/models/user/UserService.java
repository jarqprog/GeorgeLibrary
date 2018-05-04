package com.jarq.system.models.user;

public class UserService implements IUserService {


    public static IUserService getInstance() {
        return new UserService();
    }
    
    private UserService() {}




    @Override
    public IUser createUser() {
        return null;
    }
}
