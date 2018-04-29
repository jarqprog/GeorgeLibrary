package com.jarq.system.models.human.user;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteDaoUser extends SqlDao implements IDaoUser {

    public SQLiteDaoUser(Connection connection, JDBCProcessManager processManager) {
        super(connection, processManager);
    }

    @Override
    public IUser importById(int userId) throws SQLException {

        String query =  "SELECT users.id, users.email, people.fname, people.lname, people.address " +
                        "FROM users WHERE users.id=? " +
                        "INNER JOIN people on users.people_id=people.id";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.getResultSet();
        String[] userData = getProcessManager().getObjectData(resultSet);





        return null;
    }



    private IUser extractUser(String[] userData) {

        int idIndex = 0;
        int emailIndex = 1;
        int firstNameIndex = 2;
        int lastNameIndex = 3;
        int addressIndex = 4;

        int id = Integer.parseInt(userData[idIndex]);
//        String firstName =





//          public User(int id, String firstName, String lastName, String email, String password) {
//            super(id, firstName, lastName);
//            setEmail(email);
//            this.password = password;
//        }


        return null;
    }

//
//    private int id;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String address;

}
