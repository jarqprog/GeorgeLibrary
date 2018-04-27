package models.human.author;

import dao.SqlDao;
import managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;

public class SQLiteDaoAuthor extends SqlDao implements IDaoAuthor {

    public SQLiteDaoAuthor(Connection connection, JDBCProcessManager processManager) {
        super(connection, processManager);
    }

    @Override
    public IAuthor importById(int userId) {
        return null;
    }
}
