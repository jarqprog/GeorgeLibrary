package com.jarq.system.models.text;

import com.jarq.system.dao.Dao;
import com.jarq.system.exceptions.DaoFailure;

import java.util.List;

public interface IDaoText extends Dao {

    IText createNullText();

    IText createText(String title, int repositoryId)
            throws DaoFailure;

    IText importText(int textId) throws DaoFailure;

    List<IText> importTextsByRepositoryId(int repositoryId) throws DaoFailure;

    boolean updateText(IText text) throws DaoFailure;

    boolean updateTextWithContent(IText text) throws DaoFailure;

    boolean removeText(IText text) throws DaoFailure;

    boolean removeText(int textId) throws DaoFailure;

    boolean removeTextsByRepositoryId(int repositoryId) throws DaoFailure;

    IText importTextWithContent(int textId) throws DaoFailure;

}
