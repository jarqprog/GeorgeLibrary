package controllers;

import dao.DaoFactory;
import dao.IDao;
import factory.IDaoFactory;
import factory.IModelFactoryManufacture;
import models.book.Book;
import models.book.BookDao;
import models.library.ILibrary;
import models.library.LibraryFactory;
import models.worker.Author;
import models.worker.AuthorDao;
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
        this.library = modelFactoryManufacture.create(LibraryFactory.class).build(1);
        this.daoFactory = daoFactory;
        this.modelFactoryManufacture = modelFactoryManufacture;

    }

    @Override
    public void runMenu() {
        view.displayMessage("Hello!");  // tmp
        List<Book> books = daoFactory.getDAO(BookDao.class).getAllModels();

        library.setBooks(books);
        showBooks(library.getBooks());

        IDao<Author> authorDao = daoFactory.getDAO(AuthorDao.class);
        List<Author> authors = authorDao.getAllModels();
        showAuthors(authors);

        view.displayMessage(authorDao.getModelById("1").toString());
    }

    private void showBooks(List<Book> books) {
        books.forEach(b -> view.displayMessage(b.toString()));
    }

    private void showAuthors(List<Author> authors) {
        authors.forEach(a -> view.displayMessage(a.toString()));
    }
}
