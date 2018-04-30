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
        return null;
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

        int idIndex = 0;
        int emailIndex = 1;
        int firstNameIndex = 2;
        int lastNameIndex = 3;
        int addressIndex = 4;

        try {
            int id = Integer.parseInt(userData[idIndex]);
//        String firstName =
            return createNullUser();

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }

    }
}
