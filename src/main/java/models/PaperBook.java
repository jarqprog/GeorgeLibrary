package models;

public class PaperBook extends Book {
    public PaperBook(long isbn, Author author, String title,
                     Publisher publisher, int publicationYear, float price) {
        super(isbn, author, title, publisher, publicationYear, price);
    }
}
