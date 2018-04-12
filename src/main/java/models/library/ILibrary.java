package models.library;

import models.book.Book;

import java.util.List;

public interface ILibrary {

    void setBooks(List<Book> books);
    List<Book> getBooks();
    int getId();

}
