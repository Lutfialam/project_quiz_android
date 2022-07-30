package com.example.quiz.models;

import java.util.List;

public class Quiz {
    private String id;
    private String name;
    private String description;
    private int time;
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Quiz(String id, String name, String description, int time, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.questions = questions;
    }

    public Quiz(String name, String description, int time) {
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public Quiz () {}

    public Quiz(String id, String name, String description, int time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
