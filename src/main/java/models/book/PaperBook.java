package models.book;

import models.worker.Author;
import models.worker.Publisher;

public class PaperBook extends Book {
    PaperBook(String isbn, Author author, String title, Publisher publisher,
              int publicationYear, float price) {

        super(isbn, author, title, publisher, publicationYear, price);
    }
}
