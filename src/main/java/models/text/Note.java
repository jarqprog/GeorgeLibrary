package models.text;

import models.human.author.IAuthor;

public class Note extends Text {

    public Note(String title, IAuthor author) {
        super(title);
        setAuthor(author);
    }

    public Note(int id, String title, IAuthor author) {
        super(id, title);
        setAuthor(author);
    }
}
