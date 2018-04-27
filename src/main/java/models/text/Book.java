package models.text;

import models.human.author.IAuthor;

public class Book extends Text {

    public Book(String title, IAuthor author, String content) {
        super(title);
        setAuthor(author);
        setContent(content);
    }

    public Book(int id, String title, IAuthor author, String content) {
        this(title, author, content);
        setId(id);
    }
}
