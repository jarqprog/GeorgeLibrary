package com.jarq.system.models.repository;

import com.jarq.system.models.text.Text;

import java.util.List;

public interface IRepository {

    void setTexts(List<Text> texts);

    List<Text> getTexts();

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getCreationDate();

    void setCreationDate(String creationDate);

    String getLastModificationDate();

    void setLastModificationDate(String lastModificationDate);

    int getOwnerId();
}
