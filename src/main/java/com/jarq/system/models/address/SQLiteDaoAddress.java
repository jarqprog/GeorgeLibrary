package com.jarq.system.models.address;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.databaseManagers.JDBCProcessManager;
import com.jarq.system.exceptions.DaoFailure;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteDaoAddress extends SqlDao implements IDaoAddress {

    public SQLiteDaoAddress(Connection connection, JDBCProcessManager processManager) {
        super(connection, processManager);
    }

    @Override
    public IAddress createNullAddress() {
        return new NullAddress();
    }

    @Override
    public IAddress createAddress(String postalCode, String city, String street) throws SQLException, DaoFailure {
        return null;
    }

    @Override
    public IAddress importAddress(int addressId) throws SQLException, DaoFailure {
        return null;
    }

    @Override
    public boolean updateAddress(IAddress address) throws SQLException, DaoFailure {
        return false;
    }

    @Override
    public boolean exportAddress(IAddress address) throws SQLException, DaoFailure {
        return false;
    }

    @Override
    public boolean removeAddress(IAddress address) throws SQLException, DaoFailure {
        return false;
    }

    @Override
    public boolean removeAddress(int addressId) throws SQLException, DaoFailure {
        return false;
    }
}
