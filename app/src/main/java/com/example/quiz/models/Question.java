package com.example.quiz.models;

public class Question {
    private String id;
    private String question;
    private String first_choice;
    private String second_choice;
    private String third_choice;
    private String fourth_choice;
    private String answer;

    public Question(String id, String question, String first_choice, String second_choice, String third_choice, String fourth_choice, String answer) {
        this.id = id;
        this.question = question;
        this.first_choice = first_choice;
        this.second_choice = second_choice;
        this.third_choice = third_choice;
        this.fourth_choice = fourth_choice;
        this.answer = answer;
    }

    public Question(int quiz_id, String question, String first_choice, String second_choice, String third_choice, String fourth_choice, String answer) {
        this.question = question;
        this.first_choice = first_choice;
        this.second_choice = second_choice;
        this.third_choice = third_choice;
        this.fourth_choice = fourth_choice;
        this.answer = answer;
    }

    public Question() {

    }

    public Question(String id, int quiz_id, String question, String first_choice, String second_choice, String third_choice, String fourth_choice, String answer, Quiz quiz) {
        this.id = id;
        this.question = question;
        this.first_choice = first_choice;
        this.second_choice = second_choice;
        this.third_choice = third_choice;
        this.fourth_choice = fourth_choice;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirst_choice() {
        return first_choice;
    }

    public void setFirst_choice(String first_choice) {
        this.first_choice = first_choice;
    }

    public String getSecond_choice() {
        return second_choice;
    }

    public void setSecond_choice(String second_choice) {
        this.second_choice = second_choice;
    }

    public String getThird_choice() {
        return third_choice;
    }

    public void setThird_choice(String third_choice) {
        this.third_choice = third_choice;
    }

    public String getFourth_choice() {
        return fourth_choice;
    }

    public void setFourth_choice(String fourth_choice) {
        this.fourth_choice = fourth_choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
