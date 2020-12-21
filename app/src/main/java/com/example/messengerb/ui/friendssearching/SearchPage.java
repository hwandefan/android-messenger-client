package com.example.messengerb.ui.friendssearching;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.messengerb.R;
import com.example.messengerb.api.Service;
import com.example.messengerb.api.TokenHandler;
import com.example.messengerb.data.model.FriendsList;
import com.example.messengerb.data.model.OneFriendJson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPage extends Activity {
    //Services class for API and Retrofit2
    Service postsService;
    //On create logic
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);

        final EditText searchLine = (EditText)findViewById(R.id.searchLine);
        //Update information
        searchLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Call function updateSearch with main logic
                updateSearchList(searchLine.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //Create call
    Call<List<OneFriendJson>> call = null;

    //Function for getText from EditView, post it to Server. Get JSON information about users. Showing it, if is needed - Write it.
    private void updateSearchList(String searchLine){
        //Class for saving information
        final FriendsList friendsList = new FriendsList();
        //try to create retrofit object
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.16.225.145:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Connection service function for Search (API)
        postsService = retrofit.create(Service.class);
        final String token = "Bearer "+((TokenHandler) this.getApplication()).getToken();
        call = postsService.searchUser(token,searchLine);
        call.enqueue(new Callback<List<OneFriendJson>>() {
            //Create 2 listeners (On response and On Failure)
            @Override
            public void onResponse(Call<List<OneFriendJson>> call, Response<List<OneFriendJson>> response) {
                //on Respone - print List of users
                friendsList.setFriends(response.body());
                final ListView listv = (ListView) findViewById(R.id.searchlist);
                ArrayList<String> tmpList = new ArrayList<>();
                final ArrayList<OneFriendJson> friendList = new ArrayList<>(friendsList.getFriends());
                for(int i =0; i<friendList.size(); i++) {
                    tmpList.add(friendList.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchPage.this,R.layout.support_simple_spinner_dropdown_item,tmpList);
                listv.setAdapter(adapter);
                listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AddFriendClass.addFirend(friendList.get(position).getId(), token);
                        //startActivity(new Intent(SearchPage.this, AccountListChatPage.class));
                        finish();
                    }
                });
            }
            @Override
            public void onFailure(Call<List<OneFriendJson>> call, Throwable t) {
            }
        });
    }
}