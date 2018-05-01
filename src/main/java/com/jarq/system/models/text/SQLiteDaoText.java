package com.jarq.system.models.text;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTables;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.IDateTimer;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLiteDaoText extends SqlDao implements IDaoText {

    private final String defaultTable;
    private final IDateTimer dateTimer;

    public SQLiteDaoText(Connection connection, JDBCProcessManager processManager,
                         DbTables defaultTable, IDateTimer dateTimer) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
        this.dateTimer = dateTimer;
    }

    @Override
    public IText createNullText() {
        return new NullText();
    }

    @Override
    public IText createText(String title, int repositoryId) throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        String creationDate = dateTimer.getCurrentDateTime();
        IText text = new Text(id, title, creationDate, repositoryId);


        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, creationDate);
            preparedStatement.setString(4, creationDate);
            preparedStatement.setInt(5, repositoryId);
            preparedStatement.setString(6, "");  // content

            getProcessManager().executeStatement(preparedStatement);
            return text;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IText importText(int textId) throws DaoFailure {
        return null;
    }

    @Override
    public List<IText> importTextsByRepositoryId(int repositoryId) throws DaoFailure {
        return null;
    }

    @Override
    public boolean updateText(IText text) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeText(IText text) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeText(int textId) throws DaoFailure {
        return false;
    }

    @Override
    public boolean removeTextsByRepositoryId(int repositoryId) throws DaoFailure {
        return false;
    }

    @Override
    public IText importTextWithContent(int textId) throws DaoFailure {
        return null;
    }
}
