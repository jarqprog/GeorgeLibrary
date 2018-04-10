package factory;

import dao.GetableDao;
import models.Model;

public interface IDaoFactory {

    <M extends Model, T extends GetableDao<M>> T getDAO(Class<T> daoType);
}
