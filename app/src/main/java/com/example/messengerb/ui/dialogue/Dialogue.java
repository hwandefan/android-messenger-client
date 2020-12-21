package com.example.messengerb.ui.dialogue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.messengerb.R;
import com.example.messengerb.api.Service;
import com.example.messengerb.api.TokenHandler;
import com.example.messengerb.data.model.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dialogue extends Activity {
    //Services class for API and Retrofit2
    Service postsService;

    //On create logic
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);

        //Gets user id from params of current activity
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        //Load all messages between 'token' and 'id' users
        getMessages(id);

        //Link UI elements
        final Button sendMessageBtn = (Button) findViewById(R.id.sendMessage);
        final EditText editText = (EditText) findViewById(R.id.messageHandlerString);

        //Update info from server
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getMessages(id);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getMessages(id);
            }

            @Override
            public void afterTextChanged(Editable s) {
                getMessages(id);
            }
        });

        //send Message button (Call 'send message' function)
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(id,editText.getText().toString());
                editText.setText("");
            }
        });
    }

    //Load info from server api (Messages between users)
    private void getMessages(final String id) {
        Call<List<Message>> call = null;
        final ListView messagesList = (ListView) findViewById(R.id.messages);
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.16.225.145:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }
        postsService = retrofit.create(Service.class);
        String token = "Bearer "+((TokenHandler) this.getApplication()).getToken();
        call = postsService.getMessages(token,id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                ArrayList<Message> objMessages = new ArrayList<>(response.body());
                ArrayList<String> message = new ArrayList<>();
                for(int i =0; i<objMessages.size(); i++)
                {
                    //Print messages, between users
                    if(objMessages.get(i).getSenderid().equals(id))
                    {
                        message.add("He/she: "+objMessages.get(i).getMsg());
                    } else
                        {
                            message.add("You: "+objMessages.get(i).getMsg());
                        }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Dialogue.this,R.layout.support_simple_spinner_dropdown_item,message);
                messagesList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    //Send message to server in JSON format
    private void sendMessage(final String id, String message) {
        if(!(message != null || !message.equals("")))
        {
            return;
        }
        Retrofit retrofit = null;
        Call<Void> call = null;
        try {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.16.225.145:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        } catch (Exception e){
            e.printStackTrace();
        }
        postsService = retrofit.create(Service.class);
        String token = "Bearer "+((TokenHandler) this.getApplication()).getToken();
        call = postsService.sendMessages(token, message, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    getMessages(id);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
