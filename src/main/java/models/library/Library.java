package models.library;

import models.Model;
import models.book.Book;

import java.util.ArrayList;
import java.util.List;

public class Library implements Model, ILibrary {

    private List<Book> books;

    Library() {
        books = new ArrayList<>();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return this.books;
    }
}
