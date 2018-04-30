package com.jarq.system.models.repository;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTables;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.text.IDaoText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SQLiteDaoRepository extends SqlDao implements IDaoRepository {

    private final IDaoText daoText;
    private final String defaultTable;

    public SQLiteDaoRepository(Connection connection, JDBCProcessManager processManager,
                               IDaoText daoText, DbTables defaultTable) {
        super(connection, processManager);
        this.daoText = daoText;
        this.defaultTable = defaultTable.getTable();
    }

    @Override
    public IRepository createNullRepository() {
        return new NullRepository();
    }

    @Override
    public IRepository createRepository(String name, int ownerId) throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        String creationDateTime = getCurrentDateTime();
        IRepository repository = new Repository(id, name, creationDateTime, ownerId);
        repository.setLastModificationDate(creationDateTime);

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, creationDateTime);
            preparedStatement.setString(4, creationDateTime);
            preparedStatement.setInt(5, ownerId);

            getProcessManager().executeStatement(preparedStatement);

            return repository;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IRepository importRepository(int repositoryId) throws DaoFailure {
        return null;
    }

    @Override
    public List<IRepository> importAllRepositories() throws DaoFailure {
        return null;
    }

    @Override
    public boolean updateRepository(IRepository repository) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeRepository(IRepository repository) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeRepository(int repositoryId) throws DaoFailure {
        return false;
    }

    @Override
    public IRepository importRepositoryWithTexts(int repositoryId) throws DaoFailure {
        return null;
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
