package models;

public abstract class Book implements Model {

    private long isbn;
    private Author author;
    private String title;
    private Publisher publisher;
    private int publicationYear;
    private float price;


    public Book(long isbn, Author author, String title, Publisher publisher,
                int publicationYear, float price) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    public long getIsbn() {
        return isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {

        return String.format("%s - isbn %s, title '%s', publication year %s, " +
                        "price %s, author: %s, publisher: %s",
                getClass().getSimpleName(), isbn, title, publicationYear,
                price, author, publisher);
    }
}
