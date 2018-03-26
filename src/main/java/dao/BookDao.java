package dao;

import managers.databaseManagers.JDBCProcessManager;
import models.Book;

import java.sql.Connection;
import java.util.List;

public class BookDao implements GetableDao<Book>{

    private Connection connection;
    private JDBCProcessManager processManager;

    BookDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
    }


    @Override
    public Book getModelById(int id) {
        return new Book("Ania z Zielonego Wzgorza");
    }

    @Override
    public List<Book> getAllModels() {
        return null;
    }
}
