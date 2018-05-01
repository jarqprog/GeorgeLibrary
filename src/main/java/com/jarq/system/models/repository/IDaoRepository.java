package com.jarq.system.models.repository;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.util.List;

public interface IDaoRepository extends Dao {
    
    IRepository createNullRepository();

    IRepository createRepository(String name, int userId) throws DaoFailure;

    IRepository importRepository(int repositoryId) throws DaoFailure;

    List<IRepository> importRepositoriesByUserId(int userId) throws DaoFailure;

    boolean updateRepository(IRepository repository) throws DaoFailure;

    boolean removeRepository(IRepository repository) throws DaoFailure;

    boolean removeRepository(int repositoryId) throws DaoFailure;

    boolean removeRepositoriesByUserId(int userId) throws DaoFailure;

    IRepository importRepositoryWithTexts(int repositoryId) throws DaoFailure;

}
