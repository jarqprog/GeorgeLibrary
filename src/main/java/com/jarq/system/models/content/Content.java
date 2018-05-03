package com.jarq.system.models.content;

import com.jarq.system.models.Model;

public class Content extends Model implements IContent {

    private final int textId;
    private final String filepath;
    private final String creationDate;

    Content(int id, int textId, String filepath, String creationDate) {
        setId(id);
        this.textId = textId;
        this.filepath = filepath;
        this.creationDate = creationDate;
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
