package models.book;

import models.worker.Author;
import models.worker.Publisher;

public interface IBookFactory {

    Book createEBook(long isbn, Author author, String title, Publisher publisher,
                    int publicationYear, float price);

    Book createPaperBook(long isbn, Author author, String title, Publisher publisher,
                     int publicationYear, float price);
}
