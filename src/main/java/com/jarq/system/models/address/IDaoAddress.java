package com.jarq.system.models.address;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.sql.SQLException;

public interface IDaoAddress extends Dao {

    IAddress createNullAddress();

    IAddress createAddress(String postalCode, String city, String street, String houseNo)
            throws SQLException, DaoFailure;

    IAddress createAddress(String postalCode, String city, String street, String houseNo, String apartmentNo)
            throws SQLException, DaoFailure;

    IAddress importAddress(int addressId) throws SQLException, DaoFailure;

    boolean updateAddress(IAddress address) throws SQLException, DaoFailure;

    boolean exportAddress(IAddress address) throws SQLException, DaoFailure;

    boolean removeAddress(IAddress address) throws SQLException, DaoFailure;

    boolean removeAddress(int addressId) throws SQLException, DaoFailure;
}
