package models.text;

import factory.IModelFactory;
import models.human.author.IAuthor;

public class TextManufacture implements IModelFactory, ITextManufacture {

    @Override
    public Text createBook(String title, IAuthor author, String content) {
        return new Book(title, author, content);
    }

    @Override
    public Text createNote(String title, IAuthor author) {
        return new Note(title, author);
    }
}
