package models.book;

import dao.GetableDao;
import managers.databaseManagers.JDBCProcessManager;

import java.sql.Connection;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class BookDao implements GetableDao<Book> {

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
        return new ArrayList<>(Collections.singletonList(new FakeBook(
                123L, null, "zielone wzgorze",
                null, 1997, 10)));
    }
}
