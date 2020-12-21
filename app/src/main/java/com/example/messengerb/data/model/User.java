package com.example.messengerb.data.model;

//User model for JSON from API
public class User {
    private String name;
    private String login;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return login;
    }

    public void setUsername(String username) {
        this.login = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "name:\"" + name +
                "\", login:\"" + login +
                "\", password:\"" + password+
                '}';
    }
}
