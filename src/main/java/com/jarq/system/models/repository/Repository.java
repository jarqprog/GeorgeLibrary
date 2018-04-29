package com.jarq.system.models.repository;

import com.jarq.system.models.text.Text;

import java.util.List;

public class Repository implements IRepository {

    private int id;
    private String name;
    private String creationDate;
    private String lastModificationDate;
    private List<Text> texts;
    private final int ownerId;

    Repository(String name, String creationDate, int ownerId) {
        this.id = 0;
        this.name = name;
        this.creationDate = creationDate;
        this.lastModificationDate = "n/a";
        this.ownerId = ownerId;
    }

    Repository(int id, String name, String creationDate,
               String lastModificationDate, int ownerId) {
        this(name, creationDate, ownerId);
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }

    @Override
    public List<Text> getTexts() {
        return texts;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public int getOwnerId() {
        return ownerId;
    }
}
