package com.jarq.system.models.repository;

import com.jarq.system.models.NullModel;

public class NullRepository extends NullModel implements IRepository {
    
    NullRepository() {}

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

