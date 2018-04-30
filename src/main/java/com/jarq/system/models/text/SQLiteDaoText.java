package com.jarq.system.models.text;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTables;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.util.List;

public class SQLiteDaoText extends SqlDao implements IDaoText {

    private final String defaultTable;

    public SQLiteDaoText(Connection connection, JDBCProcessManager processManager,
                            DbTables defaultTable) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
    }

    @Override
    public IText createNullText() {
        return new NullText();
    }

    @Override
    public IText createText(int id, String title, String creationDate, int repositoryId) throws DaoFailure {
        return null;
    }

    @Override
    public IText importText(int textId) throws DaoFailure {
        return null;
    }

    @Override
    public List<IText> importAllTexts() throws DaoFailure {
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
    public IText importTextWithContent(int textId) throws DaoFailure {
        return null;
    }
}
