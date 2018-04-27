package controllers;

import dao.IDaoFactory;
import factory.IModelFactoryManufacture;
import models.repository.IRepository;
import models.repository.RepositoryManufacture;
import views.IRepositoryView;

public class RepositoryController implements IRepositoryController {

    private IRepositoryView view;
    private IRepository library;
    private IDaoFactory daoFactory;
    private IModelFactoryManufacture modelFactoryManufacture;

    public static IRepositoryController getInstance(
            IRepositoryView view,
            IDaoFactory daoFactory,
            IModelFactoryManufacture modelFactoryManufacture) {

        return new RepositoryController(view, daoFactory, modelFactoryManufacture);
    }

    private RepositoryController(
            IRepositoryView view,
            IDaoFactory daoFactory,
            IModelFactoryManufacture modelFactoryManufacture) {

        this.view = view;
        this.library = modelFactoryManufacture.create(RepositoryManufacture.class).build(1);
        this.daoFactory = daoFactory;
        this.modelFactoryManufacture = modelFactoryManufacture;

    }

    @Override
    public void runMenu() {
        view.displayMessage("Hello!");  // tmp
    }

}
