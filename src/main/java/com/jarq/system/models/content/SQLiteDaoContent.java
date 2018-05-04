package com.jarq.system.models.content;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTable;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.text.IText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoContent extends SqlDao implements IDaoContent {

    private final String defaultTable;
    private final IRepositoryPath repositoryPath;

    public SQLiteDaoContent(Connection connection, JDBCProcessManager processManager,
                            DbTable defaultTable, IRepositoryPath repositoryPath) {
        super(connection, processManager);
        this.defaultTable = defaultTable.getTable();
        this.repositoryPath = repositoryPath;
    }

    @Override
    public IContent createNullContent() {
        return new NullContent();
    }

    @Override
    public IContent createContent(IText text) throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        String filepath = repositoryPath.filepath(text);
        String creationDate = text.getModificationDate();
        int textId = text.getId();

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, filepath);
            preparedStatement.setString(3, creationDate);
            preparedStatement.setInt(4, textId);

            getProcessManager().executeStatement(preparedStatement);
            return new Content(id, filepath, creationDate, textId);

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IContent importContent(int contentId) throws DaoFailure {

        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, contentId);
            return extractContent(preparedStatement);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IContent> importContentsByTextId(int textId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE text_id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);
            List<String[]> dataCollection = getProcessManager().getObjectsDataCollection(preparedStatement);
            List<IContent> contents = new ArrayList<>();

            for(String[] data : dataCollection) {
                contents.add(extractContentFromTable(data));
            }
            return contents;

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IContent> importContentsByText(IText text) throws DaoFailure {
        return null;
    }

    @Override
    public boolean removeContent(IContent content) throws DaoFailure {
        return removeContent(content.getId());
    }

    @Override
    public boolean removeContent(int contentId) throws DaoFailure {
        String query = String.format("DELETE FROM %s WHERE id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, contentId);
            return getProcessManager().executeStatement(preparedStatement);
        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeContentsByTextId(int textId) throws DaoFailure {

        String query = String.format("SELECT id FROM %s WHERE text_id=? ", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, textId);
            List<String[]> nestedCollection = getProcessManager().getObjectsDataCollection(preparedStatement);
            List<Integer> idsCollection = gatherIdFromNestedList(nestedCollection);
            boolean isDone = false;
            for(int id : idsCollection) {
                isDone = removeContent(id);
            }
            return isDone;
        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeContentsByText(IText text) throws DaoFailure {
        return removeContentsByTextId(text.getId());
    }

    private IContent extractContent(PreparedStatement preparedStatement) throws DaoFailure {
        String[] contentData = getProcessManager().getObjectData(preparedStatement);
        if(contentData.length > 0) {
            return extractContentFromTable(contentData);
        } else {
            return createNullContent();
        }
    }

    private IContent extractContentFromTable(String[] contentData) throws DaoFailure {

        int ID_INDEX = 0;
        int FILE_PATH_INDEX = 1;
        int CREATION_DATE_INDEX = 2;
        int TEXT_ID_INDEX = 3;
        
        try {
            int id = Integer.parseInt(contentData[ID_INDEX]);
            String filepath = contentData[FILE_PATH_INDEX];
            String creationDate = contentData[CREATION_DATE_INDEX];
            int textId = Integer.parseInt(contentData[TEXT_ID_INDEX]);

            return new Content(id, filepath, creationDate, textId);

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
