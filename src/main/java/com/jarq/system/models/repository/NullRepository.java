package com.jarq.system.models.repository;

import com.jarq.system.models.text.Text;

import java.util.ArrayList;
import java.util.List;

public class NullRepository implements IRepository {

    private final String notAvailable = "n/a";

    NullRepository() {}

    @Override
    public void setTexts(List<Text> texts) {}

    @Override
    public List<Text> getTexts() {
        return new ArrayList<>();
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {}

    @Override
    public String getName() {
        return notAvailable;
    }

    @Override
    public void setName(String name) {}

    @Override
    public String getCreationDate() {
        return notAvailable;
    }

    @Override
    public void setCreationDate(String creationDate) {}

    @Override
    public String getLastModificationDate() {
        return notAvailable;
    }

    @Override
    public void setLastModificationDate(String lastModificationDate) {}

    @Override
    public int getOwnerId() {
        return 0;
    }

    @Override
    public String toString() {
        return "NullRepository{" +
                "notAvailable='" + notAvailable + '\'' +
                '}';
    }
}

