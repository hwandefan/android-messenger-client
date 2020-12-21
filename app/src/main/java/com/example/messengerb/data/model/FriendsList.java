package com.example.messengerb.data.model;

import java.util.List;

//List of Friends Saver
public class FriendsList {
    private List<OneFriendJson> friends;

    public List<OneFriendJson> getFriends() {
        return friends;
    }

    public void setFriends(List<OneFriendJson> friends) {
        this.friends = friends;
    }
}
