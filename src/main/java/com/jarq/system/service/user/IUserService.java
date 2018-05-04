package com.jarq.system.service.user;

import com.jarq.system.service.IService;

public interface IUserService extends IService {

    String createUser(String name, String surname, String email);

    boolean createNote(int userId, int repositoryId, String title, String content);
    
}
