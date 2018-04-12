package models.book;

import models.worker.Author;
import models.Model;
import models.worker.Publisher;

public abstract class Book implements Model {

    private long isbn;
    private Author author;
    private String title;
    private Publisher publisher;
    private int publicationYear;
    private float price;


    Book(long isbn, Author author, String title, Publisher publisher,
                int publicationYear, float price) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    @Override
    public String toString() {

        return String.format("%s - isbn %s, title '%s', publication year %s, " +
                        "price %s, author: %s, publisher: %s",
                getClass().getSimpleName(), isbn, title, publicationYear,
                price, author, publisher);
    }
}
