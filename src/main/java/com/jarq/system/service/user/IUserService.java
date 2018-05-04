package com.jarq.system.service.user;

import com.jarq.system.exceptions.ServiceException;
import com.jarq.system.service.IService;

public interface IUserService extends IService {

    String createUser(String name, String surname, String email);

    String changeUserName(int userId, String name);

    String changeUserSurname(int userId, String surname);

    String changeUserEmail(int userId, String email) throws ServiceException;

    String changeUserPassword(int userId, String password) throws ServiceException;

}
