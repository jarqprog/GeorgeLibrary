package models.book;

import models.worker.Author;
import models.worker.Publisher;

public class FakeBook extends Book {

    FakeBook(String isbn, Author author, String title, Publisher publisher, int publicationYear, float price) {
        super(isbn, author, title, publisher, publicationYear, price);
    }

//    FakeBook(String title) {
//        this.title = title;
//    }

}
