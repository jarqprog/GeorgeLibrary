package com.jarq.system.models.text;

import com.jarq.system.models.NullModel;

public class NullText extends NullModel implements IText {

    NullText() {}

    @Override
    public int getRepositoryId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return getNotAvailable();
    }

    @Override
    public void setTitle(String title) {}

    @Override
    public String getCreationDate() {
        return getNotAvailable();
    }

    @Override
    public String getModificationDate() {
        return getNotAvailable();
    }

    @Override
    public void setModificationDate(String modificationDate) {}

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public String toString() {
        return "NullText{} " + super.toString();
    }
}
