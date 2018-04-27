package models.repository;

import factory.IModelFactory;

public class RepositoryManufacture implements IModelFactory {

    public IRepository build(int id) {
        return new Repository(id);
    }

}
