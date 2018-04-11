package models.book;

import models.worker.Author;
import models.worker.Publisher;

public class EBook extends Book {

    EBook(String isbn, Author author, String title, Publisher publisher,
        int publicationYear, float price) {

        super(isbn, author, title, publisher, publicationYear, price);
    }
}
