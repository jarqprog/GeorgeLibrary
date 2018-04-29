package com.jarq.models.text;

import com.jarq.models.human.author.IAuthor;

public class Book extends Text {

    public Book(String title, IAuthor author) {
        super(title);
        setAuthor(author);
    }

    public Book(int id, String title, IAuthor author) {
        this(title, author);
        setId(id);
    }
}
