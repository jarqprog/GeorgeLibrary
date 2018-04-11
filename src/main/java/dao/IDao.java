package dao;

import models.Model;

import java.util.List;

public interface IDao<T extends Model> {

    T getModelById(int id);
    List<T> getAllModels();

}