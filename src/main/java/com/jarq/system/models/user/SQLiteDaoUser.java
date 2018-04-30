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
    public IUser importUser(int UserId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, UserId);
            String[] userData = getProcessManager().getObjectData(preparedStatement);
            return extractUser(userData);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IUser> importAllUsers() throws DaoFailure {
        return null;
    }

    @Override
    public boolean updateUser(IUser User) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeUser(IUser User) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeUser(int UserId) throws DaoFailure {
        return false;
    }

    @Override
    public IUser importUserWithRepositories(int UserId) throws DaoFailure {
        return null;
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
