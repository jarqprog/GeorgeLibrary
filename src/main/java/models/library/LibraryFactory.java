package models.library;

import factory.IModelFactory;

public class LibraryFactory implements IModelFactory {

    public ILibrary build(int id) {
        return new Library(id);
    }

}
