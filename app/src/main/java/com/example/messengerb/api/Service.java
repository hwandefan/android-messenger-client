package com.example.messengerb.api;

import com.example.messengerb.data.model.FriendsList;
import com.example.messengerb.data.model.Message;
import com.example.messengerb.data.model.OneFriendJson;
import com.example.messengerb.data.model.User;
import com.example.messengerb.data.model.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//Services (For API and Retrofit2 Libary)
public interface Service {
    @Headers({
            "Content-type: application/json"
    })
    @POST("user/signup")
    Call<Void> sendPosts(@Body User usersData);

    @Headers({
            "Content-type: application/json"
    })
    @POST("user/signin")
    Call<Object> getToken(@Body UserLogin userData);

    @Headers({
            "Content-type: application/json"
    })
    @GET("user/get_friends")
    Call<List<OneFriendJson>> getFriends(@Header("Authorization") String token);

    @Headers({
            "Content-type: application/json"
    })
    @GET("user/search/")
    Call<List<OneFriendJson>> searchUser(@Header("Authorization") String token,
                                         @Query("search") String searchLine);

    @Headers({
            "Content-type: application/json"
    })
    @GET("user/add_friend/")
    Call<String> addFriend(@Header("Authorization") String token,
                           @Query("idFriend") String id);

    @Headers({
            "Content-type: application/json"
    })
    @GET("chat/getMessages/")
    Call<List<Message>> getMessages(@Header("Authorization") String token,
                                    @Query("whom") String id);

    @Headers({
            "Content-type: application/json"
    })
    @POST("chat/send/")
    Call<Void> sendMessages(@Header("Authorization") String token,
                            @Query("message") String message,
                            @Query("whom") String id);
}
