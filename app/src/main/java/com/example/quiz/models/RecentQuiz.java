package com.example.quiz.models;

public class RecentQuiz {
    private String id;
    private int score;
    private int time;
    private User user;
    private Quiz quiz;
    private String created_at;
    private String updated_at;

    public RecentQuiz(String id, int score, int time, User user, Quiz quiz, String created_at, String updated_at) {
        this.id = id;
        this.score = score;
        this.time = time;
        this.user = user;
        this.quiz = quiz;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public RecentQuiz(int score, int time, User user, Quiz quiz, String created_at, String updated_at) {
        this.score = score;
        this.time = time;
        this.user = user;
        this.quiz = quiz;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

class RecentQuizz {
    private int id;
    private String user_id;
    private int quiz_id;
    private int total;
    private String created_at;
    private Quiz quiz;

    public RecentQuizz(int id, String user_id, int quiz_id, int total, String created_at, Quiz quiz) {
        this.id = id;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.total = total;
        this.created_at = created_at;
        this.quiz = quiz;
    }

    public RecentQuizz(String user_id, int quiz_id, int total, String created_at, Quiz quiz) {
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.total = total;
        this.created_at = created_at;
        this.quiz = quiz;
    }

    public RecentQuizz(String user_id, int quiz_id, int total, String created_at) {
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.total = total;
        this.created_at = created_at;
    }

    public RecentQuizz() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
