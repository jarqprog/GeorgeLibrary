package com.jarq.system.models.text;

import com.jarq.system.models.Model;

public class Text extends Model implements IText {

    private String title;
    private String creationDate;
    private String modificationDate = "-";
    private String content = "-";
    private int repositoryId;

    Text(int id, String title, String creationDate, int repositoryId) {
        setId(id);
        this.title = title;
        this.creationDate = creationDate;
        this.repositoryId = repositoryId;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
