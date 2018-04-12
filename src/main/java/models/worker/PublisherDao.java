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

public class PublisherDao implements IDao<Publisher> {

    private Connection connection;
    private JDBCProcessManager processManager;
    private final String table;

    public PublisherDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
        this.table = DbTables.PUBLISHERS.getTable();
    }

    @Override
    public Publisher getModelById(String id) {


        final String query = String.format("SELECT * FROM %s WHERE publisher_id=?", table);

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) ) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            String[] data = processManager.getObjectData(rs);

            return extractPublisher(data);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();

            return null;  // fake publisher?
        }
    }

    @Override
    public List<Publisher> getAllModels() {
        List<Publisher> publishers= new ArrayList<>();
        final String query = String.format("SELECT * FROM %s", table);

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query) ) {
            ResultSet rs = preparedStatement.executeQuery();
            List<String[]> dataCollection = processManager.getObjectsDataCollection(rs);
            for(String[] data : dataCollection) {
                publishers.add(extractPublisher(data));
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return publishers;
    }

    private Publisher extractPublisher(String[] data) throws NumberFormatException {
        final int idIndex = 0;
        final int nameIndex = 1;
        final int cityIndex = 2;
        final int countryIndex = 3;

        String id = data[idIndex];
        String name = data[nameIndex];
        String city = data[cityIndex];
        String country = data[countryIndex];

        return new Publisher(id, name, city, country);
    }
}
