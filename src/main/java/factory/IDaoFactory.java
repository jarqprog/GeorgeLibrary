package factory;

import dao.IDao;
import models.Model;

public interface IDaoFactory {

    <M extends Model, T extends IDao<M>> T getDAO(Class<T> daoType);
}
