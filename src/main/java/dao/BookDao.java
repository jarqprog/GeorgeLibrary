package dao;

import managers.databaseManagers.JDBCProcessManager;
import models.Book;
import models.FakeBook;

import java.sql.Connection;
import java.util.List;

public class BookDao implements GetableDao<FakeBook>{

    private Connection connection;
    private JDBCProcessManager processManager;

    BookDao(Connection connection, JDBCProcessManager processManager) {
        this.connection = connection;
        this.processManager = processManager;
    }


    @Override
    public FakeBook getModelById(int id) {
        return new FakeBook("Ania z Zielonego Wzgorza");
    }

    @Override
    public List<FakeBook> getAllModels() {
        return null;
    }
}
