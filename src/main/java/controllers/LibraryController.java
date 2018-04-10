package controllers;

import dao.GetableDao;
import factory.IDaoFactory;
import factory.IModelFactoryManufacture;
import models.book.Book;
import models.book.BookDao;
import models.library.ILibrary;
import views.ILibraryView;

import java.util.List;

public class LibraryController implements ILibraryController {

    private ILibraryView view;
    private ILibrary library;
    private IDaoFactory daoFactory;
    private IModelFactoryManufacture modelFactoryManufacture;

    public static ILibraryController getInstance(
            ILibraryView view,
            ILibrary library,
            IDaoFactory daoFactory,
            IModelFactoryManufacture modelFactoryManufacture) {

        return new LibraryController(view, library, daoFactory, modelFactoryManufacture);
    }

    private LibraryController(
            ILibraryView view, ILibrary library,
            IDaoFactory daoFactory,
            IModelFactoryManufacture modelFactoryManufacture) {

        this.view = view;
        this.library = library;
        this.daoFactory = daoFactory;
        this.modelFactoryManufacture = modelFactoryManufacture;
    }

    @Override
    public void runMenu() {
        view.displayMessage("Hello!");  // tmp
        List<Book> books = daoFactory.getDAO(BookDao.class).getAllModels();
    }
}
