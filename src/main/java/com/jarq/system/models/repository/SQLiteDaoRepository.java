package com.jarq.system.models.repository;

import com.jarq.system.dao.SqlDao;
import com.jarq.system.enums.DbTables;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.IDateTimer;
import com.jarq.system.managers.databaseManagers.JDBCProcessManager;
import com.jarq.system.models.text.IDaoText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoRepository extends SqlDao implements IDaoRepository {

    private final IDaoText daoText;
    private final String defaultTable;
    private final IDateTimer dateTimer;

    public SQLiteDaoRepository(Connection connection, JDBCProcessManager processManager,
                               IDaoText daoText, DbTables defaultTable,
                               IDateTimer dateTimer) {
        super(connection, processManager);
        this.daoText = daoText;
        this.defaultTable = defaultTable.getTable();
        this.dateTimer = dateTimer;
    }

    @Override
    public IRepository createNullRepository() {
        return new NullRepository();
    }

    @Override
    public IRepository createRepository(String name, int userId) throws DaoFailure {

        int id = getLowestFreeIdFromGivenTable(defaultTable);
        String creationDateTime = dateTimer.getCurrentDateTime();
        IRepository repository = new Repository(id, name, creationDateTime, userId);
        repository.setLastModificationDate(creationDateTime);

        String query = String.format("INSERT INTO %s " +
                "VALUES(?, ?, ?, ?, ?)", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, creationDateTime);
            preparedStatement.setString(4, creationDateTime);
            preparedStatement.setInt(5, userId);

            getProcessManager().executeStatement(preparedStatement);

            return repository;

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IRepository importRepository(int repositoryId) throws DaoFailure {
        String query = String.format("SELECT * FROM %s WHERE id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, repositoryId);
            String[] repositoryData = getProcessManager().getObjectData(preparedStatement);
            return extractRepository(repositoryData);

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public List<IRepository> importRepositoriesByUserId(int userId) throws DaoFailure {
        List<IRepository> repositories = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE user_id=?", defaultTable);
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, userId);

            List<String[]> repositoriesData = getProcessManager()
                    .getObjectsDataCollection(preparedStatement);
            for(String[] data : repositoriesData) {
                repositories.add(extractRepository(data));
            }
            return repositories;

        } catch(SQLException | DaoFailure ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean updateRepository(IRepository repository) throws DaoFailure {

        int id = repository.getId();
        String name = repository.getName();
        String creationDate = repository.getCreationDate();
        String lastModificationDate = repository.getLastModificationDate();
        int userId = repository.getUserId();

        String query = String.format(   "UPDATE %s SET name=?, creation_date=?, last_modification_date=?, " +
                "user_id=? WHERE id=?", defaultTable);

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, creationDate);
            preparedStatement.setString(3, lastModificationDate);
            preparedStatement.setInt(4, userId);
            preparedStatement.setInt(5, id);

            return getProcessManager().executeStatement(preparedStatement);

        } catch(SQLException ex){
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeRepository(IRepository repository) throws DaoFailure {
        return removeRepository(repository.getId());
    }

    @Override
    public boolean removeRepository(int repositoryId) throws DaoFailure {
        String query = String.format("DELETE FROM %s WHERE id=?", defaultTable);

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, repositoryId);
//            boolean isRepositoryRemoved = getProcessManager().executeStatement(preparedStatement);
//            boolean areTextsRemoved = daoText.removeTextsByRepositoryId(repositoryId);
//            return isRepositoryRemoved && areTextsRemoved;
            return getProcessManager().executeStatement(preparedStatement);

        } catch (SQLException ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public boolean removeRepositoriesByUserId(int userId) throws DaoFailure {
        String query = String.format("SELECT id FROM %s WHERE user_id=?", defaultTable);

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            List<String[]> nestedCollection = getProcessManager().getObjectsDataCollection(preparedStatement);
            List<Integer> idCollection = gatherIdFromNestedList(nestedCollection);
            for(int repositoryId : idCollection) {
                removeRepository(repositoryId);
            }
            return true;

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }

    @Override
    public IRepository importRepositoryWithTexts(int repositoryId) throws DaoFailure {
        return null;
    }

    private IRepository extractRepository(String[] repositoryData) throws DaoFailure {

        int ID_INDEX = 0;
        int NAME_INDEX = 1;
        int CREATION_DATE_INDEX = 2;
        int MODIFICATION_DATE_INDEX = 3;
        int USER_ID_INDEX = 4;

        try {
            int id = Integer.parseInt(repositoryData[ID_INDEX]);
            String name = repositoryData[NAME_INDEX];
            String creationDate = repositoryData[CREATION_DATE_INDEX];
            String lastModificationDate = repositoryData[MODIFICATION_DATE_INDEX];
            int userId = Integer.parseInt(repositoryData[USER_ID_INDEX]);

            IRepository repository = new Repository(id, name, creationDate, userId);
            repository.setLastModificationDate(lastModificationDate);

            return repository;

        } catch (Exception ex) {
            throw new DaoFailure(ex.getMessage());
        }
    }
}
