package models.text;

import models.human.author.IAuthor;

public interface ITextManufacture {

    Text createBook(String title, IAuthor author, String content);
    Text createNote(String title, IAuthor author);
}
