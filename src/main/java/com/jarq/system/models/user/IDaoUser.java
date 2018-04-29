package com.jarq.system.models.user;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.IAddress;

import java.sql.SQLException;

public interface IDaoUser extends Dao {

    IUser createNullUser();

    IUser createUser(String name, String surname, String email, IAddress address) throws SQLException, DaoFailure;

    IUser importUser(int userId) throws SQLException, DaoFailure;

    boolean updateUser(IUser user) throws SQLException, DaoFailure;

    boolean exportUser(IUser user) throws SQLException, DaoFailure;

    boolean removeUser(IUser user) throws SQLException, DaoFailure;

    boolean removeUser(int userId) throws SQLException, DaoFailure;
}
