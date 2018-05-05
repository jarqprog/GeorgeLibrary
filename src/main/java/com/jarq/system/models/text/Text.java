package com.jarq.system.models.text;

import com.jarq.system.models.Model;

public class Text extends Model implements IText {

    private String title;
    private final String creationDate;
    private String modificationDate = "-";
    private final int repositoryId;
    private final int userId;


    Text(int id, String title, String creationDate, int repositoryId, int userId) {
        setId(id);
        this.title = title;
        this.creationDate = creationDate;
        this.repositoryId = repositoryId;
        this.userId = userId;
    }

    @Override
    public int getRepositoryId() {
        return repositoryId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    @Override
    public String getModificationDate() {
        return modificationDate;
    }

    @Override
    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public int getUserId() {
        return userId;
    }

}
