package com.jarq.system.models.text;

public class NullText implements IText {
    
    private String notAvailable = "n/a";

    NullText() {}

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getRepositoryId() {
        return 0;
    }

    @Override
    public void setRepositoryId(int repositoryId) {

    }

    @Override
    public String getTitle() {
        return notAvailable;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public String getCreationDate() {
        return notAvailable;
    }

    @Override
    public void setCreationDate(String creationDate) {

    }

    @Override
    public String getModificationDate() {
        return notAvailable;
    }

    @Override
    public void setModificationDate(String modificationDate) {

    }

    @Override
    public String getContent() {
        return notAvailable;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public String toString() {
        return "NullText{" +
                "notAvailable='" + notAvailable + '\'' +
                '}';
    }
}
