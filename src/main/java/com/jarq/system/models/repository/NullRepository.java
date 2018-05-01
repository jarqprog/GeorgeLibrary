package com.jarq.system.models.repository;

import com.jarq.system.models.text.IText;
import com.jarq.system.models.NullModel;

import java.util.ArrayList;
import java.util.List;

public class NullRepository extends NullModel implements IRepository {
    
    NullRepository() {}

    @Override
    public void setTexts(List<IText> texts) {}

    @Override
    public List<IText> getTexts() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return getNotAvailable();
    }

    @Override
    public void setName(String name) {}

    @Override
    public String getCreationDate() {
        return getNotAvailable();
    }

    @Override
    public void setCreationDate(String creationDate) {}

    @Override
    public String getLastModificationDate() {
        return getNotAvailable();
    }

    @Override
    public void setLastModificationDate(String lastModificationDate) {}

    @Override
    public int getUserId() {
        return 0;
    }


    @Override
    public String toString() {
        return "NullRepository{} " + super.toString();
    }
}

