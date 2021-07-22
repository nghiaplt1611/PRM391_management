package com.example.myapplication.model;

import java.io.Serializable;

public class Scores implements Serializable {
    private int changeQuestionScore;
    private int hintScore;
    private int initialScore;
    private int passedLevelScore;
    private int wrongAnswerScore;

    public Scores() {
    }

    public Scores(int changeQuestionScore, int hintScore, int initialScore, int passedLevelScore, int wrongAnswerScore) {
        this.changeQuestionScore = changeQuestionScore;
        this.hintScore = hintScore;
        this.initialScore = initialScore;
        this.passedLevelScore = passedLevelScore;
        this.wrongAnswerScore = wrongAnswerScore;
    }

    public int getChangeQuestionScore() {
        return changeQuestionScore;
    }

    public void setChangeQuestionScore(int changeQuestionScore) {
        this.changeQuestionScore = changeQuestionScore;
    }

    public int getHintScore() {
        return hintScore;
    }

    public void setHintScore(int hintScore) {
        this.hintScore = hintScore;
    }

    public int getInitialScore() {
        return initialScore;
    }

    public void setInitialScore(int initialScore) {
        this.initialScore = initialScore;
    }

    public int getPassedLevelScore() {
        return passedLevelScore;
    }

    public void setPassedLevelScore(int passedLevelScore) {
        this.passedLevelScore = passedLevelScore;
    }

    public int getWrongAnswerScore() {
        return wrongAnswerScore;
    }

    public void setWrongAnswerScore(int wrongAnswerScore) {
        this.wrongAnswerScore = wrongAnswerScore;
    }
}
