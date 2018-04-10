package factory;

import models.book.BookFactory;
import models.worker.AuthorFactory;

public class ModelFactoryManufacture implements IModelFactoryManufacture {

    @Override
    public <T extends IModelFactory> T create(Class<T> type) {

        String factoryName = type.getSimpleName();
        IModelFactory factory = null;

        switch(factoryName) {
            case("BookFactory"):
                factory = new BookFactory();
                break;
            case("AuthorFactory"):
                factory = new AuthorFactory();
                break;
        }
        return type.cast(factory);
    }
}
