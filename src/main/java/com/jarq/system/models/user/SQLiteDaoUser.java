package com.jarq.system.models.user;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTables;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.Address;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoUser extends SqlDao implements IDaoUser {

    private final IDaoAddress daoAddress;
    private final String defaultTable;

    public SQLiteDaoUser(Connection connection, JDBCProcessManager processManager,
                         IDaoAddress daoAddress, DbTables defaultTable) {
        super(connection, processManager);
        this.daoAddress = daoAddress;
        this.defaultTable = defaultTable.getTable();
    }

    @Override
    public IUser createNullUser() {
        return new NullUser(daoAddress);
    }

    @Override
    public IUser createUser(String name, String surname, String email, IAddress address) throws DaoFailure {
        int id = getLowestFreeIdFromGivenTable(defaultTable);
        final String temporaryPassword = "123";
        IUser user = new User(id, name, surname, email, temporaryPassword, address);

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, temporaryPassword);
            preparedStatement.setInt(6, address.getId());

            getProcessManager().executeStatement(preparedStatement);

            return user;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IUser importUser(int userId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, userId);
            String[] userData = getProcessManager().getObjectData(preparedStatement);
            return extractUser(userData);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IUser> importAllUsers() throws DaoFailure {
        List<IUser> users = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            List<String[]> usersData = getProcessManager().getObjectsDataCollection(preparedStatement);
            for(String[] data : usersData) {
                users.add(extractUser(data));
            }
            return users;

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean updateUser(IUser user) throws DaoFailure {

        int id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        String password = user.getPassword();
        int addressId = user.getAddress().getId();

        String query = String.format(   "UPDATE %s SET name=?, surname=?, email=?, " +
                "password=?, address_id=? " +
                "WHERE id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, addressId);
            preparedStatement.setInt(6, id);

            return getProcessManager().executeStatement(preparedStatement);

        } catch(SQLException ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeUser(IUser user) throws DaoFailure {
        return removeUser(user.getId());
    }

    @Override
    public boolean removeUser(int userId) throws DaoFailure {
        String query = String.format("DELETE FROM %s WHERE id=?", defaultTable);

        int addressId = importUser(userId).getAddress().getId();

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            boolean isUserRemoved = getProcessManager().executeStatement(preparedStatement);
            boolean isAddressRemoved = daoAddress.removeAddress(addressId);

            // repositories should be removed
            return isUserRemoved && isAddressRemoved;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IUser importUserWithRepositories(int userId) throws DaoFailure {
        return createNullUser();
    }

    private IUser extractUser(String[] userData) throws DaoFailure {

        int ID_INDEX = 0;
        int NAME_INDEX = 1;
        int SURNAME_INDEX = 2;
        int EMAIL_INDEX = 3;
        int PASSWORD_INDEX = 4;
        int ADDRESS_INDEX = 5;

        try {
            int id = Integer.parseInt(userData[ID_INDEX]);
            String name = userData[NAME_INDEX];
            String surname = userData[SURNAME_INDEX];
            String email = userData[EMAIL_INDEX];
            String password = userData[PASSWORD_INDEX];
            int addressId = Integer.parseInt(userData[ADDRESS_INDEX]);
            IAddress address = daoAddress.importAddress(addressId);

            return new User(id, name, surname, email, password, address);

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
