package com.jarq.system.models.content;

import com.jarq.system.models.NullModel;

public class NullContent extends NullModel implements IContent {

    @Override
    public int getTextId() {
        return 0;
    }

    @Override
    public String getFilepath() {
        return getNotAvailable();
    }

    @Override
    public String getCreationDate() {
        return getNotAvailable();
    }
}
