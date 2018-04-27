package factory;

import models.text.TextManufacture;
import models.repository.RepositoryManufacture;

public class ModelFactoryManufacture implements IModelFactoryManufacture {

    @Override
    public <T extends IModelFactory> T create(Class<T> type) {

        String factoryName = type.getSimpleName();
        IModelFactory factory = null;

        switch(factoryName) {
            case("TextManufacture"):
                factory = new TextManufacture();
                break;
            case("RepositoryManufacture"):
                factory = new RepositoryManufacture();
                break;
        }
        return type.cast(factory);
    }
}
