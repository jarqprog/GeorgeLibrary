package com.jarq.system.models.address;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbLabels;
import com.jarq.system.enums.DbTables;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.exceptions.DaoFailure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteDaoAddress extends SqlDao implements IDaoAddress {

    private final String defaultTable = DbTables.ADDRESSES.getTable();

    public SQLiteDaoAddress(Connection connection, JDBCProcessManager processManager) {
        super(connection, processManager);
    }

    @Override
    public IAddress createNullAddress() {
        return new NullAddress();
    }

    @Override
    public IAddress createAddress(String postalCode, String city, String street, String houseNo)
            throws DaoFailure {
        String emptyApartmentNo = "";
        return createAddress(postalCode, city, street, houseNo, emptyApartmentNo);
    }

    @Override
    public IAddress createAddress(String postalCode, String city, String street, String houseNo, String apartmentNo)
            throws DaoFailure {
        IAddress address = new Address(postalCode, city, street, houseNo);
        address.setApartmentNo(apartmentNo);

        // idsBefore & idsAfter to get id for new created address:
        String[] idsBefore = getCurrentValuesCollectionFromGivenLabel(DbTables.ADDRESSES, DbLabels.ID);
        boolean isSaved = saveAddress(address);
        if(! isSaved ) {
            throw new DaoFailure();
        }
        String[] idsAfter = getCurrentValuesCollectionFromGivenLabel(DbTables.ADDRESSES, DbLabels.ID);
        String newId = findNewValueInCollection(idsBefore, idsAfter);

        try {
            int addressId = Integer.parseInt(newId);
            address.setId(addressId);
            saveAddress(address);
            return address;
        } catch (NumberFormatException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IAddress importAddress(int addressId) throws DaoFailure {
        return null;
    }

    @Override
    public boolean exportAddress(IAddress address) throws DaoFailure {
        return saveAddress(address);
    }

    @Override
    public boolean removeAddress(IAddress address) throws DaoFailure {
        return removeAddress(address.getId());
    }

    @Override
    public boolean removeAddress(int addressId) throws DaoFailure {
        try {
            String query = String.format("DELETE FROM %s WHERE id=?", defaultTable);
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, addressId);
            return getProcessManager().executeStatement(preparedStatement);
        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private boolean saveAddress(IAddress address) throws DaoFailure {

        String nullModelId = "-2";
        String newAddressId = "-1";
        try {
            String addressId = String.valueOf(address.getId());
            if(addressId.equals(nullModelId)) {  // address is nullModel - skip saving
                throw new DaoFailure("Blocked saving null object");
            }

            String postalCode = address.getPostalCode();
            String city = address.getCity();
            String street = address.getStreet();
            String houseNo = address.getHouseNo();
            String apartmentNo = address.getApartmentNo();

            String query;
            if (addressId.equals(newAddressId)) {  // it's brand new address, hasn't valid id
                query = String.format(
                        "INSERT INTO %s " +
                                "VALUES(null, ?, ?, ?, ?, ?)", defaultTable);
            } else {
                query = String.format(
                        "UPDATE %s SET postal_code=?, city=?, street=?, " +
                                "house_no=?, apartment_no=? " +
                                "WHERE id=?", defaultTable);
            }

            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, postalCode);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.setString(4, houseNo);
            preparedStatement.setString(5, apartmentNo);

            if (!addressId.equals(newAddressId)) {
                preparedStatement.setInt(6, Integer.valueOf(addressId));
            }
            return getProcessManager().executeStatement(preparedStatement);
        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
