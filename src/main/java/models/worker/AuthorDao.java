package models.worker;

import dao.IDao;
import enums.DbTables;
import managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao implements IDao <Author> {

    private Connection connection;
    private JDBCProcessManager processManager;
    private final String table;

    public AuthorDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
        this.table = DbTables.AUTHORS.getTable();
    }

//    author_id,name,surname,birth_year,city,country
//1,Tom,Hanks,1956,Concord,USA
//2,Dexter,Dias,1967,London,UK


    @Override
    public Author getModelById(String id) {

        final String query = String.format("SELECT * FROM %s WHERE author_id=?", table);
        int authorId = Integer.parseInt(id);

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) ) {
            preparedStatement.setInt(1, authorId);
            ResultSet rs = preparedStatement.executeQuery();

            String[] data = processManager.getObjectData(rs);

            return extractAuthor(data);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();

            return new FakeAuthor();
        }
    }

    @Override
    public List<Author> getAllModels() {
        List<Author> authors = new ArrayList<>();
        final String query = String.format("SELECT * FROM %s", table);

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) ) {
            ResultSet rs = preparedStatement.executeQuery();
            List<String[]> dataCollection = processManager.getObjectsDataCollection(rs);
            for(String[] data : dataCollection) {
                authors.add(extractAuthor(data));
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return authors;
    }

    private Author extractAuthor(String[] data) throws NumberFormatException {
        final int idIndex = 0;
        final int nameIndex = 1;
        final int surnameIndex = 2;
        final int birthYearIndex = 3;
        final int cityIndex = 4;
        final int countryIndex = 5;

        int id = Integer.parseInt(data[idIndex]);
        String name = data[nameIndex];
        String surname = data[surnameIndex];
        int birthYear = Integer.parseInt(data[birthYearIndex]);
        String city = data[cityIndex];
        String country = data[countryIndex];

        return new Author(id, name, surname, birthYear, city, country);
    }

}
