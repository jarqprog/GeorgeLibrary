package models.book;

import factory.IModelFactory;
import models.worker.Author;
import models.worker.Publisher;

public class BookFactory implements IModelFactory, IBookFactory {


    @Override
    public Book createEBook(String isbn, Author author, String title,
                            Publisher publisher, int publicationYear,
                            float price) {

        return new EBook(isbn, author, title, publisher, publicationYear, price);
    }

    @Override
    public Book createPaperBook(String isbn, Author author, String title,
                                Publisher publisher, int publicationYear,
                                float price) {

        return new PaperBook(isbn, author, title, publisher, publicationYear, price);
    }
}
