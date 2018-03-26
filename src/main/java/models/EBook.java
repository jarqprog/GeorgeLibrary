package models;

public class EBook extends Book {

    public EBook(long isbn, Author author, String title, Publisher publisher,
                 int publicationYear, float price) {
        super(isbn, author, title, publisher, publicationYear, price);
    }

}
