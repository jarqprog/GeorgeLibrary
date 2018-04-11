package controllers;

import factory.IDaoFactory;
import factory.IModelFactoryManufacture;
import models.book.Book;
import models.book.BookDao;
import models.library.ILibrary;
import models.library.LibraryFactory;
import views.ILibraryView;

import java.util.List;

public class LibraryController implements ILibraryController {

    private ILibraryView view;
    private ILibrary library;
    private IDaoFactory daoFactory;
    private IModelFactoryManufacture modelFactoryManufacture;

    public static ILibraryController getInstance(
            ILibraryView view,
            IDaoFactory daoFactory,
            IModelFactoryManufacture modelFactoryManufacture) {

        return new LibraryController(view, daoFactory, modelFactoryManufacture);
    }

    private LibraryController(
            ILibraryView view,
            IDaoFactory daoFactory,
            IModelFactoryManufacture modelFactoryManufacture) {

        this.view = view;
        this.library = modelFactoryManufacture.create(LibraryFactory.class).build();
        this.daoFactory = daoFactory;
        this.modelFactoryManufacture = modelFactoryManufacture;

    }

    @Override
    public void runMenu() {
        view.displayMessage("Hello!");  // tmp
        List<Book> books = daoFactory.getDAO(BookDao.class).getAllModels();

        library.setBooks(books);
        showBooks(library.getBooks());
    }

    private void showBooks(List<Book> books) {
        books.forEach(b -> view.displayMessage(b.toString()));
    }
}
