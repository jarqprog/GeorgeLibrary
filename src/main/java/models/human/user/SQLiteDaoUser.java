package models.human.user;

import dao.SqlDao;
import managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;

public class SQLiteDaoUser extends SqlDao implements IDaoUser {

    public SQLiteDaoUser(Connection connection, JDBCProcessManager processManager) {
        super(connection, processManager);
    }

    @Override
    public IUser importById(int userId) {
        return null;
    }
}
