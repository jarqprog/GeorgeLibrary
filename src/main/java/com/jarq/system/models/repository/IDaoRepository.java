package com.jarq.system.models.repository;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.address.IAddress;

import java.util.List;

public interface IDaoRepository extends Dao {
    
    IRepository createNullRepository();

    IRepository createRepository(String name, String surname, String email, IAddress address)
            throws DaoFailure;

    IRepository importRepository(int repositoryId) throws DaoFailure;

    List<IRepository> importAllRepositories() throws DaoFailure;

    boolean updateRepository(IRepository repository) throws DaoFailure;

    boolean removeRepository(IRepository repository) throws DaoFailure;

    boolean removeRepository(int repositoryId) throws DaoFailure;

    IRepository importRepositoryWithTexts(int repositoryId) throws DaoFailure;

}
