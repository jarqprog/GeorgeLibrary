package com.jarq.system.models.human.user;

import java.sql.SQLException;

public interface IDaoUser {

    IUser importById(int userId) throws SQLException;
}
