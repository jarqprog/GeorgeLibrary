package com.jarq.system.models.content;

import com.jarq.system.models.Model;

public class Content extends Model implements IContent {

    private final String filepath;
    private final String creationDate;
    private final int textId;

    Content(int id, String filepath, String creationDate, int textId) {
        setId(id);
        this.filepath = filepath;
        this.creationDate = creationDate;
        this.textId = textId;
    }

    @Override
    public int getTextId() {
        return textId;
    }

    @Override
    public String getFilepath() {
        return filepath;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Content{" +
                "textId=" + textId +
                ", filepath='" + filepath + '\'' +
                ", creationDate='" + creationDate + '\'' +
                "} " + super.toString();
    }
}
