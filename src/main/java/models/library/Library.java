package models.library;

import models.Model;
import models.book.Book;

import java.util.ArrayList;
import java.util.List;

public class Library implements Model, ILibrary {

    private List<Book> books;
    private int id;

    Library(int id) {
        this.id = id;
        books = new ArrayList<>();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public int getId() {
        return id;
    }
}
