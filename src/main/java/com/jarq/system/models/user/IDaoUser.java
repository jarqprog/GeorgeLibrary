package com.jarq.system.models.user;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.util.List;

public interface IDaoUser extends Dao {

    IUser createNullUser();

    IUser createUser(String name, String surname, String email)
            throws DaoFailure;

    IUser importUser(int userId) throws DaoFailure;

    IUser importUserByMail(String email) throws DaoFailure;

    IUser importUserWithAddress(int userId) throws DaoFailure;

    List<IUser> importAllUsers() throws DaoFailure;

    boolean updateUser(IUser user) throws DaoFailure;

    boolean removeUser(IUser user) throws DaoFailure;

    boolean removeUser(int userId) throws DaoFailure;
}
