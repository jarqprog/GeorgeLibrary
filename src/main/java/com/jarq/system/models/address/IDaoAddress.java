package com.jarq.system.models.address;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.util.List;

public interface IDaoAddress extends Dao {

    IAddress createNullAddress();

    IAddress createAddress(String postalCode, String city,
                           String street, String houseNo, int userId)
            throws DaoFailure;

    IAddress createAddress(String postalCode, String city,
                           String street, String houseNo,
                           String apartmentNo, int userId)
            throws DaoFailure;

    IAddress importAddress(int addressId) throws DaoFailure;

    IAddress importAddressByUserId(int userId) throws DaoFailure;

    List<IAddress> importAllAddresses() throws DaoFailure;

    boolean updateAddress(IAddress address) throws DaoFailure;

    boolean removeAddressByUserId(int userId) throws DaoFailure;

    boolean removeAddress(IAddress address) throws DaoFailure;

    boolean removeAddress(int addressId) throws DaoFailure;
}
