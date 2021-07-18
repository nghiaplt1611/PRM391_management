package com.example.myapplication.model;

import java.io.Serializable;

public class Question implements Serializable {
    private String id;
    private String imageURL;
    private String answer;
    private int level;
    private boolean status;

    public Question() {
    }

    public Question(String id, String imageURL, String answer, int level, boolean status) {
        this.id = id;
        this.imageURL = imageURL;
        this.answer = answer;
        this.level = level;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
