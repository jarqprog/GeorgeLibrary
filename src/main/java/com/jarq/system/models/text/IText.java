package com.jarq.system.models.text;

import com.jarq.system.models.Identifiable;

public interface IText extends Identifiable {

    int getId();

    int getRepositoryId();

    String getTitle();

    void setTitle(String title);

    String getCreationDate();

    String getModificationDate();

    void setModificationDate(String modificationDate);

    int getUserId();
}
