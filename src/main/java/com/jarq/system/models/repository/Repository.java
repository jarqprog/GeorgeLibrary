package com.jarq.system.models.repository;

import com.jarq.system.models.Model;

public class Repository extends Model implements IRepository {

    private String name;
    private String creationDate;
    private String lastModificationDate = "-";
    private final int userId;

    Repository(int id, String name, String creationDate, int userId) {
        setId(id);
        this.name = name;
        this.creationDate = creationDate;
        this.userId = userId;
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

}
