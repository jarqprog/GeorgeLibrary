package com.jarq.system.models.repository;

import com.jarq.system.models.text.IText;

import java.util.List;

public interface IRepository {

    void setTexts(List<IText> texts);

    List<IText> getTexts();

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getCreationDate();

    void setCreationDate(String creationDate);

    String getLastModificationDate();

    void setLastModificationDate(String lastModificationDate);

    int getUserId();
}
