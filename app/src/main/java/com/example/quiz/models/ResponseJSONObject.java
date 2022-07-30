package com.example.quiz.models;

public class ResponseJSONObject {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorUserMessage getMessage() {
        return message;
    }

    public void setMessage(ErrorUserMessage message) {
        this.message = message;
    }

    private String status;
    private ErrorUserMessage message;
}