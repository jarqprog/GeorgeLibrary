package com.jarq.system.models.repository;

import com.jarq.system.models.Model;
import com.jarq.system.models.text.IText;

import java.util.ArrayList;
import java.util.List;

public class Repository extends Model implements IRepository {

    private String name;
    private String creationDate;
    private String lastModificationDate = "-";
    private List<IText> texts = new ArrayList<>();
    private final int userId;

    Repository(int id, String name, String creationDate, int userId) {
        setId(id);
        this.name = name;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    @Override
    public void setTexts(List<IText> texts) {
        this.texts = texts;
    }

    @Override
    public List<IText> getTexts() {
        return texts;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModificationDate='" + lastModificationDate + '\'' +
                ", texts=" + texts +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
