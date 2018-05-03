package com.jarq.system.models.text;

import com.jarq.system.models.Identifiable;
import com.jarq.system.models.content.IContent;

import java.util.List;

public interface IText extends Identifiable {

    int getId();

    int getRepositoryId();

    String getTitle();

    void setTitle(String title);

    String getCreationDate();

    String getModificationDate();

    void setModificationDate(String modificationDate);

    List<IContent> getContents();

    void setContents(List<IContent> contents);

    int getUserId();
}
