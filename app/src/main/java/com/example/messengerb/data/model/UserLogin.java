package com.example.messengerb.data.model;

//Additional Class to post JSON to API server for login
public class UserLogin {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                "\", login:\"" + login +
                "\", password:\"" + password+
                '}';
    }
}
