package com.example.messengerb.ui.accountlistchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.messengerb.R;
import com.example.messengerb.api.Service;
import com.example.messengerb.api.TokenHandler;
import com.example.messengerb.data.model.FriendsList;
import com.example.messengerb.data.model.OneFriendJson;
import com.example.messengerb.ui.dialogue.Dialogue;
import com.example.messengerb.ui.friendssearching.SearchPage;
import com.example.messengerb.ui.login.LoginPage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountListChatPage extends Activity {
    //Services class for API and Retrofit2
    Service postsService;

    //On create logic
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountlistchat);

        //if token doesn't exists or wrong - exit to login page
        if(((TokenHandler) this.getApplication()).getToken().equals("") || ((TokenHandler) this.getApplication()).getToken() == null){
            startActivity(new Intent(AccountListChatPage.this, LoginPage.class));
        }

        //Calling main logic
        sendPost();

        //Add listener to change activity (add Friends activty)
        final Button addBtn = (Button) findViewById(R.id.add_friend);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountListChatPage.this, SearchPage.class));
            }
        });
    }
    Call<List<OneFriendJson>> call = null;
    private void sendPost()
    {
        //Create get Retrofit2 request for API server with token
        final FriendsList friendsList = new FriendsList();
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

            //Change token standard for server
            String token = "Bearer "+((TokenHandler) this.getApplication()).getToken();
            call = postsService.getFriends(token);

            call.enqueue(new Callback<List<OneFriendJson>>() {
                //Create 2 listeners (On response and On Failure)
                @Override
                public void onResponse(Call<List<OneFriendJson>> call, Response<List<OneFriendJson>> response) {
                    //on good Respone - print list of friends by token
                    friendsList.setFriends(response.body());
                    final ListView listv = (ListView) findViewById(R.id.listv);
                    ArrayList<String> tmpList = new ArrayList<>();
                    final ArrayList<OneFriendJson> friendList = new ArrayList<>(friendsList.getFriends());
                    for(int i =0; i<friendList.size(); i++) {
                        tmpList.add(friendList.get(i).getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AccountListChatPage.this,R.layout.support_simple_spinner_dropdown_item,tmpList);
                    listv.setAdapter(adapter);
                    listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent chatIntent = new Intent(AccountListChatPage.this, Dialogue.class);
                            chatIntent.putExtra("id",friendList.get(position).getId());
                            startActivity(chatIntent);

                        }
                    });
                }

                @Override
                public void onFailure(Call<List<OneFriendJson>> call, Throwable t) {
                    friendsList.setFriends(null);
                }
            });
    }
}
