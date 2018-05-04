package com.jarq.system.models.text;

import com.jarq.system.models.Model;
import com.jarq.system.models.content.IContent;

import java.util.ArrayList;
import java.util.List;

public class Text extends Model implements IText {

    private String title;
    private final String creationDate;
    private String modificationDate = "-";
    private List<IContent> contents = new ArrayList<>();
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
    public List<IContent> getContents() {
        return contents;
    }

    @Override
    public void setContents(List<IContent> contents) {
        this.contents = contents;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Text{" +
                "title='" + title + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", contents=" + contents +
                ", repositoryId=" + repositoryId +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
