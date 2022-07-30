package com.example.quiz.models;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String password_confirmation;
    private String level;

    public User(String id, String name, String email, String password, String level) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
    }

    public User(String name, String email, String password, String level) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
    }

    public User(String name, String email, String password, String[] password_confirmation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation[0];
    }

    public User(String name, String email, String level) {
        this.name = name;
        this.email = email;
        this.level = level;
    }

    public User() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
