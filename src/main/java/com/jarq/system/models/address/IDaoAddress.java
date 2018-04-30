package com.jarq.system.models.address;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.sql.SQLException;

public interface IDaoAddress extends Dao {

    IAddress createNullAddress();

    IAddress createAddress(String postalCode, String city,
                           String street, String houseNo)
            throws DaoFailure;

    IAddress createAddress(String postalCode, String city,
                           String street, String houseNo,
                           String apartmentNo)
            throws DaoFailure;

    IAddress importAddress(int addressId) throws DaoFailure;

    boolean exportAddress(IAddress address) throws DaoFailure;

    boolean removeAddress(IAddress address) throws DaoFailure;

    boolean removeAddress(int addressId) throws DaoFailure;
}
