package com.example.messengerb.ui.friendssearching;
import com.example.messengerb.api.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AddFriendClass {
    //static function for (@POST) selected user to server and add him to friend list
    public static void addFirend(String id, String token){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.16.225.145:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Service postsService = retrofit.create(Service.class);
            Call<String> call = postsService.addFriend(token,id);

            //It is needed to override, but on Respone and on Failure only change activity
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
