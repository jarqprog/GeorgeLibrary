package models.book;

import dao.IDao;
import managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class BookDao implements IDao<Book> {

    private Connection connection;
    private JDBCProcessManager processManager;

    public BookDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
    }

    @Override
    public Book getModelById(int id) {
        return new FakeBook(
                "12321421", null, "zielone wzgorze",
                null, 1997, 10);
    }

    @Override
    public List<Book> getAllModels() {

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * from Books;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return new ArrayList<>(Collections.singletonList(new FakeBook(
                "12321421", null, "zielone wzgorze",
                null, 1997, 10)));
    }
}
