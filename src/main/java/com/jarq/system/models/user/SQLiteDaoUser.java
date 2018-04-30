package com.jarq.system.models.user;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteDaoUser extends SqlDao implements IDaoUser {

    private final IDaoAddress daoAddress;

    public SQLiteDaoUser(Connection connection, JDBCProcessManager processManager,
                         IDaoAddress daoAddress) {
        super(connection, processManager);
        this.daoAddress = daoAddress;
    }

    @Override
    public IUser createNullUser() {
        return new NullUser(daoAddress);
    }

    @Override
    public IUser createUser(String name, String surname, String email, IAddress address)
            throws SQLException, DaoFailure {

                String query =  "SELECT users.id, users.email, people.fname, people.lname, people.address " +
                        "FROM users WHERE users.id=? " +
                        "INNER JOIN people on users.people_id=people.id";

//        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
//        preparedStatement.setInt(1, userId);
//        ResultSet resultSet = preparedStatement.getResultSet();
//        String[] userData = getProcessManager().getObjectData(resultSet);
        return createNullUser();
    }

    @Override
    public IUser importUser(int userId) throws SQLException, DaoFailure {
        return createNullUser();
    }

    @Override
    public boolean updateUser(IUser user) throws SQLException, DaoFailure {
        return false;
    }

    @Override
    public boolean exportUser(IUser user) throws SQLException, DaoFailure {
        return false;
    }

    @Override
    public boolean removeUser(IUser user) throws SQLException, DaoFailure {
        return false;
    }

    @Override
    public boolean removeUser(int userId) throws SQLException, DaoFailure {
        return false;
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
