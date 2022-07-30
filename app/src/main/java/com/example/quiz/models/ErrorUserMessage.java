package com.example.quiz.models;

public class ErrorUserMessage {
    private String[] name;
    private String[] email;
    private String[] password;
    private String[] password_confirmation;

    public String getName() {
        return name == null ? "" : name[0];
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String getEmail() {
        return email == null ? "" : email[0];
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String getPassword() {
        return password == null ? "" : password[0];
    }

    public void setPassword(String[] password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation == null ? "" : password_confirmation[0];
    }

    public void setPassword_confirmation(String[] password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}