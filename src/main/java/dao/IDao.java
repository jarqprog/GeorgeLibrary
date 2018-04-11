package dao;

import models.Model;

import java.util.List;

public interface GetableDao<T extends Model> {

    T getModelById(int id);
    List<T> getAllModels();

}