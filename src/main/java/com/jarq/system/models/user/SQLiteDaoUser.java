package com.jarq.system.models.user;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.exceptions.DaoFailure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoUser extends SqlDao implements IDaoUser {

    private final String defaultTable;

    public SQLiteDaoUser(Connection connection, JDBCProcessManager processManager,
                         DbTable defaultTable) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
    }

    @Override
    public IUser createNullUser() {
        return new NullUser();
    }

    @Override
    public IUser createUser(String name, String surname, String email) throws DaoFailure {
        int id = getLowestFreeIdFromGivenTable(defaultTable);
        final String temporaryPassword = "123";
        IUser user = new User(id, name, surname, email, temporaryPassword);

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, temporaryPassword);

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
            return extractUser(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IUser importUserByMail(String email) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE email=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, email);
            return extractUser(preparedStatement);

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
                users.add(extractUserFromTable(data));
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

        String query = String.format(   "UPDATE %s SET name=?, surname=?, email=?, " +
                                        "password=? WHERE id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, id);

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

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            return getProcessManager().executeStatement(preparedStatement);

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private IUser extractUser(PreparedStatement preparedStatement) throws DaoFailure, SQLException {
        String[] userData = getProcessManager().getObjectData(preparedStatement);
        if(userData.length > 0) {
            return extractUserFromTable(userData);
        } else {
            throw new DaoFailure("There's no such user in database!");
        }
    }

    private IUser extractUserFromTable(String[] userData) throws DaoFailure {

        int ID_INDEX = 0;
        int NAME_INDEX = 1;
        int SURNAME_INDEX = 2;
        int EMAIL_INDEX = 3;
        int PASSWORD_INDEX = 4;

        try {
            int id = Integer.parseInt(userData[ID_INDEX]);
            String name = userData[NAME_INDEX];
            String surname = userData[SURNAME_INDEX];
            String email = userData[EMAIL_INDEX];
            String password = userData[PASSWORD_INDEX];

            return new User(id, name, surname, email, password);

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
