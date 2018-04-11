package factory;

public interface IModelFactoryManufacture {

    <T extends IModelFactory> T create(Class<T> type);
}
