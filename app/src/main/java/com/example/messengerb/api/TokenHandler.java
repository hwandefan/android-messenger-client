package com.example.messengerb.api;

import android.app.Application;

public class TokenHandler extends Application {

    //Class for Authorization token
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String someVariable) {
        this.token = someVariable;
    }
}
