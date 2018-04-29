package com.jarq.system.models.text;

public class Text {

    private int id;
    private int repositoryId;
    private String title;
    private String creationDate;
    private String modificationDate;
    private String content;

    public Text(int repositoryId, String title) {
        this.id = -1;  // default for new Text which doesn't exists in database
        this.repositoryId = repositoryId;
        this.title = title;
        this.creationDate = "n/a";
        this.modificationDate = "n/a";
        this.content = "n/a";
    }

    public Text(int id, int repositoryId, String title, String creationDate,
                String modificationDate) {
        this(repositoryId, title);
        this.id = id;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.content = "n/a";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
