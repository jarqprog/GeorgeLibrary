package com.jarq.system.models.repository;

import com.jarq.system.models.Model;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Repository extends Model implements IRepository {

    private String name;
    private String creationDate;
    private String lastModificationDate = "-";
    private List<IText> texts = new ArrayList<>();
    private final int ownerId;

    Repository(int id, String name, String creationDate, int ownerId) {
        setId(id);
        this.name = name;
        this.creationDate = creationDate;
        this.ownerId = ownerId;
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
    public int getOwnerId() {
        return ownerId;
    }
}
