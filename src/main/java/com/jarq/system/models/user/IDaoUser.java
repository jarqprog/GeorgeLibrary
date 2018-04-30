package com.jarq.system.models.user;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.IAddress;

import java.util.List;

public interface IDaoUser extends Dao {

    IUser createNullUser();

    IUser createUser(String name, String surname, String email, IAddress address)
            throws DaoFailure;

    IUser importUser(int UserId) throws DaoFailure;

    List<IUser> importAllUsers() throws DaoFailure;

    boolean updateUser(IUser User) throws DaoFailure;

    boolean removeUser(IUser User) throws DaoFailure;

    boolean removeUser(int UserId) throws DaoFailure;

    IUser importUserWithRepositories(int UserId) throws DaoFailure;
}
