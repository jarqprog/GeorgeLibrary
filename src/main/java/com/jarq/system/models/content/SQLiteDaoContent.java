package com.jarq.system.models.content;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLiteDaoContent extends SqlDao implements IDaoContent {

    private final String defaultTable;
    private final IDateTimer dateTimer;

    public SQLiteDaoContent(Connection connection, JDBCProcessManager processManager,
                            DbTable defaultTable, IDateTimer dateTimer) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
        this.dateTimer = dateTimer;
    }

    @Override
    public IContent createNullContent() {
        return new NullContent();
    }

    @Override
    public IContent createContent(int textId, String filepath) throws DaoFailure {
        int id = getLowestFreeIdFromGivenTable(defaultTable);
        String creationDate = dateTimer.getCurrentDateTime();

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, filepath);
            preparedStatement.setString(3, creationDate);
            preparedStatement.setInt(4, textId);

            getProcessManager().executeStatement(preparedStatement);
            return new Content(id, textId, filepath, creationDate);

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IContent importContent(int contentId) throws DaoFailure {
        return null;
    }

    @Override
    public List<IContent> importContentsByTextId(int textId) throws DaoFailure {
        return null;
    }

    @Override
    public boolean removeContent(IContent content) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeContent(int contentId) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeContentsByTextId(int textId) throws DaoFailure {
        return false;
    }
}
