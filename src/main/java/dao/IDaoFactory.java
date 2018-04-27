package dao;

public interface IDaoFactory {

    <T extends Dao> T createDAO(Class<T> daoType);
}