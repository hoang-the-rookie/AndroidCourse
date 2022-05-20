package edu.hanu.mynotes.models;

import java.io.Serializable;

public class Note implements Serializable {
    private long id;
    private String title;
    private String content;

//    constructor for getting from table
    public Note(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

//    constructor for create
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
