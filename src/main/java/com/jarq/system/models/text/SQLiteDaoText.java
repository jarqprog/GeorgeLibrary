package com.jarq.system.models.text;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.IDateTimer;
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

        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);
            return extractTextFromStatement(preparedStatement, false);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }


    @Override
    public IText importTextWithContent(int textId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);
            return extractTextFromStatement(preparedStatement, true);

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
                texts.add(extractTextFromTable(data, false));
            }
            return texts;

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean updateText(IText text) throws DaoFailure {
        return update(text, false);
    }

    @Override
    public boolean updateTextWithContent(IText text) throws DaoFailure {
        return update(text, true);
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

    private IText extractTextFromStatement(PreparedStatement preparedStatement, boolean withContent)
            throws DaoFailure {

        String[] textData = getProcessManager().getObjectData(preparedStatement);
        return extractTextFromTable(textData, withContent);
    }

    private IText extractTextFromTable(String[] textData, boolean withContent) throws DaoFailure {
        //  without content to avoid overloading system

        int ID_INDEX = 0;
        int TITLE_INDEX = 1;
        int CREATION_DATE_INDEX = 2;
        int LAST_MODIFICATION_DATE_INDEX = 3;
        int REPOSITORY_ID_INDEX = 4;

        try {
            int id = Integer.parseInt(textData[ID_INDEX]);
            String title = textData[TITLE_INDEX];
            String creationDate = textData[CREATION_DATE_INDEX];
            String lastModificationDate = textData[LAST_MODIFICATION_DATE_INDEX];
            int repositoryId = Integer.parseInt(textData[REPOSITORY_ID_INDEX]);

            IText text = new Text(id, title, creationDate, repositoryId);

            if(withContent) {  // probably it will be changed (filepath in database, text in file)
                int CONTENT_INDEX = 5;
                String content = textData[CONTENT_INDEX];
                text.setContent(content);
            }

            text.setModificationDate(lastModificationDate);
            return text;

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    private boolean update(IText text, boolean shouldUpdateContent) throws DaoFailure {

        // todo (content will be kept in file)

        int id = text.getId();
        String title = text.getTitle();
        String creationDate = text.getCreationDate();
        String lastModificationDate = text.getModificationDate();
        int repositoryId = text.getRepositoryId();

        String query;

        if(shouldUpdateContent) {
            query = String.format("UPDATE %s SET title=?, creation_date=?, " +
                    "last_modification_date=?, repository_id=?, content=? " +
                    "WHERE id=?", defaultTable);
        } else {
            query = String.format("UPDATE %s SET title=?, creation_date=?, " +
                    "last_modification_date=?, repository_id=? " +
                    "WHERE id=?", defaultTable);
        }
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, creationDate);
            preparedStatement.setString(3, lastModificationDate);
            preparedStatement.setInt(4, repositoryId);

            if(shouldUpdateContent) {
                String content = text.getContent();
                preparedStatement.setString(5, content);
                preparedStatement.setInt(6, id);
            } else {
                preparedStatement.setInt(5, id);
            }
            return getProcessManager().executeStatement(preparedStatement);

        } catch(SQLException ex){
            throw new DaoFailure(ex.getMessage());
        }
    }
}
