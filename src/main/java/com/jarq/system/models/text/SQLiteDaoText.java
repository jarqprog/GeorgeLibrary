package com.jarq.system.models.text;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.user.IUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoText extends SqlDao implements IDaoText {

    private final String defaultTable;
    private final IDateTimer dateTimer;

    public SQLiteDaoText(Connection connection, JDBCProcessManager processManager,
                         DbTable defaultTable, IDateTimer dateTimer) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
        this.dateTimer = dateTimer;
    }

    @Override
    public IText createNullText() {
        return new NullText();
    }

    @Override
    public IText createText(IRepository repository, String title) throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        int repositoryId = repository.getId();
        int userId = repository.getUserId();
        String creationDate = dateTimer.getCurrentDateTime();
        IText text = new Text(id, title, creationDate, repositoryId, userId);
        text.setModificationDate(creationDate);

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, creationDate);
            preparedStatement.setString(4, creationDate);
            preparedStatement.setInt(5, repositoryId);
            preparedStatement.setInt(6, userId);

            getProcessManager().executeStatement(preparedStatement);
            return text;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IText importText(int textId) throws DaoFailure {

        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);
            return extractText(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IText> importTextsByRepositoryId(int repositoryId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE repository_id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, repositoryId);
            return extractTexts(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IText> importTextsByRepository(IRepository repository) throws DaoFailure {
        return importTextsByRepositoryId(repository.getId());
    }

    @Override
    public List<IText> importTextsByUser(IUser user) throws DaoFailure {

        int userId = user.getId();
        String query = String.format("SELECT * FROM %s WHERE user_id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, userId);
            return extractTexts(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean updateText(IText text) throws DaoFailure {
        return update(text);
    }

    @Override
    public boolean removeText(IText text) throws DaoFailure {
        return removeText(text.getId());
    }

    @Override
    public boolean removeText(int textId) throws DaoFailure {

        String query = String.format("DELETE FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);
            return getProcessManager().executeStatement(preparedStatement);

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private IText extractText(PreparedStatement preparedStatement)
            throws DaoFailure {

        String[] textData = getProcessManager().getObjectData(preparedStatement);
        if(textData.length > 0) {
            return extractTextFromTable(textData);
        } else {
            throw new DaoFailure("There's no such text in database!");
        }
    }

    private List<IText> extractTexts(PreparedStatement preparedStatement)
            throws DaoFailure {
        try {
            List<String[]> dataCollection = getProcessManager().getObjectsDataCollection(preparedStatement);
            List<IText> texts = new ArrayList<>();

            for(String[] data : dataCollection) {
                texts.add(extractTextFromTable(data));
            }
            return texts;
        } catch (DaoFailure ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private IText extractTextFromTable(String[] textData) throws DaoFailure {

        int ID_INDEX = 0;
        int TITLE_INDEX = 1;
        int CREATION_DATE_INDEX = 2;
        int LAST_MODIFICATION_DATE_INDEX = 3;
        int REPOSITORY_ID_INDEX = 4;
        int USER_ID_INDEX = 5;

        try {
            int id = Integer.parseInt(textData[ID_INDEX]);
            String title = textData[TITLE_INDEX];
            String creationDate = textData[CREATION_DATE_INDEX];
            String lastModificationDate = textData[LAST_MODIFICATION_DATE_INDEX];
            int repositoryId = Integer.parseInt(textData[REPOSITORY_ID_INDEX]);
            int userId = Integer.parseInt(textData[USER_ID_INDEX]);

            IText text = new Text(id, title, creationDate, repositoryId, userId);
            text.setModificationDate(lastModificationDate);

            return text;

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private boolean update(IText text) throws DaoFailure {

        int id = text.getId();
        String title = text.getTitle();
        String lastModificationDate = text.getModificationDate();

        // other values are constant
        String query = String.format(   "UPDATE %s SET title=?, last_modification_date=? " +
                                        "WHERE id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, lastModificationDate);
            preparedStatement.setInt(3, id);

            return getProcessManager().executeStatement(preparedStatement);

        } catch(SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
