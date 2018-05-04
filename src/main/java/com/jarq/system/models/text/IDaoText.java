package com.jarq.system.models.text;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.user.IUser;

import java.util.List;

public interface IDaoText extends Dao {

    IText createNullText();

    IText createText(IRepository repository, String title)
            throws DaoFailure;

    IText importText(int textId) throws DaoFailure;

    List<IText> importTextsByRepositoryId(int repositoryId) throws DaoFailure;

    List<IText> importTextsByRepository(IRepository repository) throws DaoFailure;

    List<IText> importTextsByUser(IUser user) throws DaoFailure;

    boolean updateText(IText text) throws DaoFailure;

    boolean removeText(IText text) throws DaoFailure;

    boolean removeText(int textId) throws DaoFailure;

    boolean removeTextsByRepositoryId(int repositoryId) throws DaoFailure;

    boolean removeTextsByRepository(IRepository repository) throws DaoFailure;

    boolean removeTextsByUser(IUser user) throws DaoFailure;
}
