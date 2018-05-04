package com.jarq.system.models.address;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.user.IUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoAddress extends SqlDao implements IDaoAddress {

    private final String defaultTable;

    public SQLiteDaoAddress(Connection connection, JDBCProcessManager processManager,
                            DbTable defaultTable) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
    }

    @Override
    public IAddress createNullAddress() {
        return new NullAddress();
    }

    @Override
    public IAddress createAddress(IUser user, String postalCode, String city,
                                  String street, String houseNo)
            throws DaoFailure {

        String emptyApartmentNo = "-";
        return createAddress(user, postalCode, city, street, houseNo, emptyApartmentNo);
    }

    @Override
    public IAddress createAddress(IUser user, String postalCode, String city,
                                  String street, String houseNo, String apartmentNo)
            throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        int userId = user.getId();
        IAddress address = new Address(id, postalCode, city, street, houseNo, userId);
        address.setApartmentNo(apartmentNo);

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, postalCode);
            preparedStatement.setString(3, city);
            preparedStatement.setString(4, street);
            preparedStatement.setString(5, houseNo);
            preparedStatement.setString(6, apartmentNo);
            preparedStatement.setInt(7, userId);

            getProcessManager().executeStatement(preparedStatement);

            return address;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IAddress importAddress(int addressId) throws DaoFailure {

        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, addressId);
            return extractAddressFromStatement(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IAddress importAddressByUserId(int userId) throws DaoFailure {

        String query = String.format("SELECT * FROM %s WHERE user_id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, userId);
            return extractAddressFromStatement(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IAddress importAddressByUser(IUser user) throws DaoFailure {
        return importAddressByUserId(user.getId());
    }

    @Override
    public List<IAddress> importAllAddresses() throws DaoFailure {

        List<IAddress> addresses = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            List<String[]> addressesData = getProcessManager().getObjectsDataCollection(preparedStatement);
            for(String[] data : addressesData) {
                addresses.add(extractAddressFromTable(data));
            }
            return addresses;

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean updateAddress(IAddress address) throws DaoFailure {

        int id = address.getId();
        String postalCode = address.getPostalCode();
        String city = address.getCity();
        String street = address.getStreet();
        String houseNo = address.getHouseNo();
        String apartmentNo = address.getApartmentNo();
        int userId = address.getUserId();

        String query = String.format(   "UPDATE %s SET postal_code=?, city=?, street=?, " +
                                        "house_no=?, apartment_no=?, user_id=? " +
                                        "WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, postalCode);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.setString(4, houseNo);
            preparedStatement.setString(5, apartmentNo);
            preparedStatement.setInt(6, userId);
            preparedStatement.setInt(7, id);

            return getProcessManager().executeStatement(preparedStatement);

        } catch(SQLException ex){
        throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeAddress(IAddress address) throws DaoFailure {
        return removeAddress(address.getId());
    }

    @Override
    public boolean removeAddress(int addressId) throws DaoFailure {
        String query = String.format("DELETE FROM %s WHERE id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, addressId);
            return getProcessManager().executeStatement(preparedStatement);
        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeAddressByUserId(int userId) throws DaoFailure {
        String query = String.format("DELETE FROM %s WHERE user_id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, userId);
            return getProcessManager().executeStatement(preparedStatement);
        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeAddressByUser(IUser user) throws DaoFailure {
        return removeAddressByUserId(user.getId());
    }


    private IAddress extractAddressFromStatement(PreparedStatement preparedStatement) throws DaoFailure {
        String[] addressData = getProcessManager().getObjectData(preparedStatement);
        if(addressData.length > 0) {
            return extractAddressFromTable(addressData);
        } else {
            return createNullAddress();
        }
    }

    private IAddress extractAddressFromTable(String[] addressData) throws DaoFailure {

        int ID_INDEX = 0;
        int POSTAL_CODE_INDEX = 1;
        int CITY_INDEX = 2;
        int STREET_INDEX = 3;
        int HOUSE_NO_INDEX = 4;
        int APARTMENT_NO_INDEX = 5;
        int USER_ID_INDEX = 6;

        try {
            int id = Integer.parseInt(addressData[ID_INDEX]);
            String postalCode = addressData[POSTAL_CODE_INDEX];
            String city = addressData[CITY_INDEX];
            String street = addressData[STREET_INDEX];
            String houseNo = addressData[HOUSE_NO_INDEX];
            String apartmentNo = addressData[APARTMENT_NO_INDEX];
            int userId = Integer.parseInt(addressData[USER_ID_INDEX]);
            IAddress address = new Address(id, postalCode, city, street, houseNo, userId);
            address.setApartmentNo(apartmentNo);
            return address;

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
