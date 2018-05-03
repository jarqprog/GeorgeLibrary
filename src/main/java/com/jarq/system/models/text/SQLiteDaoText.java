package com.jarq.system.models.text;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;

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
    public IText createText(String title, int repositoryId, int userId) throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        String creationDate = dateTimer.getCurrentDateTime();
        IText text = new Text(id, title, creationDate, repositoryId, userId);


        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)", defaultTable);

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
            return extractTextFromStatement(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }


    @Override
    public IText importTextWithContent(int textId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);

            // implementation filepath managers

            return extractTextFromStatement(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IText> importTextsByRepositoryId(int repositoryId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE repository_id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, repositoryId);
            List<String[]> dataCollection = getProcessManager().getObjectsDataCollection(preparedStatement);
            List<IText> texts = new ArrayList<>();

            for(String[] data : dataCollection) {
                texts.add(extractTextFromTable(data));
            }
            return texts;

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean updateText(IText text) throws DaoFailure {
        return update(text);
    }

    @Override
    public boolean updateTextWithContent(IText text) throws DaoFailure {


        // it will be implementation with files managers

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

    @Override
    public boolean removeTextsByRepositoryId(int repositoryId) throws DaoFailure {

        String query = String.format("SELECT id FROM %s WHERE repository_id=? ", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, repositoryId);
            List<String[]> nestedCollection = getProcessManager().getObjectsDataCollection(preparedStatement);
            List<Integer> idsCollection = gatherIdFromNestedList(nestedCollection);
            boolean isDone = false;
            for(int id : idsCollection) {
                isDone = removeText(id);
            }
            return isDone;
        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private IText extractTextFromStatement(PreparedStatement preparedStatement)
            throws DaoFailure {

        String[] textData = getProcessManager().getObjectData(preparedStatement);
        return extractTextFromTable(textData);
    }

    private IText extractTextFromTable(String[] textData) throws DaoFailure {
        //  without filepath to avoid overloading system

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
