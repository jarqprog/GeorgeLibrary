package com.jarq.models.text;

import com.jarq.models.human.author.IAuthor;

public abstract class Text {

    private int id;
    private IAuthor author;
    private String title;
    private String date;
    private String content;

    public Text(String title) {
        this.id = -1;
        this.title = title;
        this.date = "not known";
        this.content = "";
    }

    public Text(int id, String title) {
        this.id = id;
        this.title = title;
    }


    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", human=" + author.getFullName() +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public IAuthor getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(IAuthor author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
