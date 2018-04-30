package com.jarq.system.models.text;

import com.jarq.system.models.NullModel;

public class NullText extends NullModel implements IText {

    NullText() {}

    @Override
    public int getRepositoryId() {
        return 0;
    }

    @Override
    public void setRepositoryId(int repositoryId) {}

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
    public void setCreationDate(String creationDate) {}

    @Override
    public String getModificationDate() {
        return getNotAvailable();
    }

    @Override
    public void setModificationDate(String modificationDate) {}

    @Override
    public String getContent() {
        return getNotAvailable();
    }

    @Override
    public void setContent(String content) {}

    @Override
    public String toString() {
        return "NullText{" +
                "getNotAvailable()='" + getNotAvailable() + '\'' +
                '}';
    }
}
