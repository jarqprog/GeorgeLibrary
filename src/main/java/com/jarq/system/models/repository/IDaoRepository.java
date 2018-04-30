package com.jarq.system.models.repository;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.util.List;

public interface IDaoRepository extends Dao {
    
    IRepository createNullRepository();

    IRepository createRepository(String name, int ownerId) throws DaoFailure;

    IRepository importRepository(int repositoryId) throws DaoFailure;

    List<IRepository> importRepositoriesByOwnerId(int ownerId) throws DaoFailure;

    boolean updateRepository(IRepository repository) throws DaoFailure;

    boolean removeRepository(IRepository repository) throws DaoFailure;

    boolean removeRepository(int repositoryId) throws DaoFailure;

    IRepository importRepositoryWithTexts(int repositoryId) throws DaoFailure;

}
