package com.jarq.integrationTests.controllers;

import com.jarq.system.dao.IDaoFactory;
import com.jarq.system.models.repository.IRepository;
import com.jarq.integrationTests.views.IRepositoryView;

public class RepositoryController implements IRepositoryController {

    private IRepositoryView view;
    private IRepository library;
    private IDaoFactory daoFactory;

    public static IRepositoryController getInstance(
            IRepositoryView view,
            IDaoFactory daoFactory) {

        return new RepositoryController(view, daoFactory);
    }

    private RepositoryController(
            IRepositoryView view,
            IDaoFactory daoFactory) {

        this.view = view;
        this.daoFactory = daoFactory;
    }

    @Override
    public void runMenu() {
        view.displayMessage("Hello!");  // tmp
    }

}
