package com.example.messengerb.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.messengerb.R;
import com.example.messengerb.api.Service;
import com.example.messengerb.api.TokenHandler;
import com.example.messengerb.data.model.UserLogin;
import com.example.messengerb.ui.accountlistchat.AccountListChatPage;
import com.example.messengerb.ui.register.RegisterPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends Activity {
    //Services class for API and Retrofit2
    Service postsService;

    //On create logic
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Link all UI elements with objects
        final Button signIn = (Button) findViewById(R.id.login);
        final Button register = (Button) findViewById(R.id.register);
        final EditText uName = (EditText) findViewById(R.id.username);
        final EditText uPasswd = (EditText) findViewById(R.id.password);

        //Sign in button - call sendPost function, connect to API
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://172.16.225.145:8080/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    postsService = retrofit.create(Service.class);
                    sendPost(uName.getText().toString(), uPasswd.getText().toString());

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });
    }

    //Main logic function - post data to server, if 'true' - change activity, gets token
    private void sendPost(String login, String password)
    {
        final UserLogin post = new UserLogin();
        post.setLogin(login);
        post.setPassword(password);
        Call<Object> call = postsService.getToken(post);
        call.enqueue(new Callback<Object>() {

            //On success - change activity - get token
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.body().toString().equals("Error") || response.body().toString().equals("")){
                    Button btn = (Button)findViewById(R.id.error);
                    btn.setVisibility(View.VISIBLE);
                } else {
                    ((TokenHandler) getApplication()).setToken(response.body().toString());
                    startActivity(new Intent(LoginPage.this, AccountListChatPage.class));
                }
            }

            //On failure - show error message
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Button btn = (Button)findViewById(R.id.error);
                btn.setVisibility(View.VISIBLE);
            }
        });
    }
}
