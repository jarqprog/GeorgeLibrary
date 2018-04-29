package com.jarq.system.models.text;

public interface IText {

    int getId();

    void setId(int id);

    int getRepositoryId();

    void setRepositoryId(int repositoryId);

    String getTitle();

    void setTitle(String title);

    String getCreationDate();

    void setCreationDate(String creationDate);

    String getModificationDate();

    void setModificationDate(String modificationDate);

    String getContent();

    void setContent(String content);
}
