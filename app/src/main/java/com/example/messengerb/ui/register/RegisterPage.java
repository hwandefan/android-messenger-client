package com.example.messengerb.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messengerb.R;
import com.example.messengerb.api.Service;
import com.example.messengerb.data.model.User;
import com.example.messengerb.ui.login.LoginPage;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPage extends Activity {
    //Services class for API and Retrofit2
    Service postsService;

    //On create logic
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Link UI elemens with objects
        final Button back = (Button) findViewById(R.id.back);
        final Button register = (Button) findViewById(R.id.register);
        final EditText uName = (EditText) findViewById(R.id.name);
        final EditText uPasswd = (EditText) findViewById(R.id.password);
        final EditText uLogin = (EditText) findViewById(R.id.username);

        //Back to login activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Connect with api, call sendPost() function
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://172.16.225.145:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    postsService = retrofit.create(Service.class);
                    sendPost(uName.getText().toString(),
                            uPasswd.getText().toString(),
                            uLogin.getText().toString());

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //Main logic function - post JSON data to the server
    private void sendPost(String name, String password, String username) {
        final User post = new User();
        post.setName(name);
        post.setPassword(password);
        post.setUsername(username);
        Call<Void> call = postsService.sendPosts(post);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(RegisterPage.this, LoginPage.class));
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
