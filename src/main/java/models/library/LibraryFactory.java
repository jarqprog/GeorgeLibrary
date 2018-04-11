package models.library;

import factory.IModelFactory;

public class LibraryFactory implements IModelFactory {

    public ILibrary build() {
        return new Library();
    }

}
