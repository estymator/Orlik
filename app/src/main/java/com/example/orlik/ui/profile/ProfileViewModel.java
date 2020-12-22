package com.example.orlik.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.GameRequests;
import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.User;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {
    private final static String TAG="ProfileViewModelTAG";
    private User user;
    private String loggedinUserLogin;
    private MutableLiveData<ArrayList<User>> friends = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Game>> organisedGames = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Game>> attendGames = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteFriendsResult = new MutableLiveData<>();
    private MutableLiveData<Friends> addFriendsResult = new MutableLiveData<>();
    private boolean isFriend;
    private FriendsRequests friendsRequests = new FriendsRequests();
    private GameRequests gameRequests = new GameRequests();

    public String getLoggedinUserLogin() {
        return loggedinUserLogin;
    }

    public void setLoggedinUserLogin(String loggedinUserLogin) {
        this.loggedinUserLogin = loggedinUserLogin;
    }

    public boolean isFriend() {
        return isFriend;
    }


    public void setFriend(boolean friend) {
        Log.v(TAG, "SET FRIENDS"+friend);
        isFriend = friend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public MutableLiveData<ArrayList<User>> getFriends() {
        return friends;
    }

    public MutableLiveData<ArrayList<Game>> getOrganisedGames(){
        return organisedGames;
    }

    public MutableLiveData<ArrayList<Game>> getAttendGames() {
        return attendGames;
    }

    public MutableLiveData<Boolean> getDeleteFriendsResult() {
        return deleteFriendsResult;
    }


    public MutableLiveData<Friends> getAddFriendsResult() {
        return addFriendsResult;
    }


    public void getFriendsOfUser(){
        friendsRequests.loadFriends(user.getLogin(),friends);
    }

    public void getUserOrganisedGames(){
        gameRequests.loadOrganisedGames(loggedinUserLogin, organisedGames);
    }

    public void getUserAttendGames(){
        gameRequests.loadAttendGames(loggedinUserLogin, attendGames);
    }

    public void addFriends(){
        friendsRequests.addFriends(loggedinUserLogin, user.getLogin(), addFriendsResult);
    }

    public void deleteFriends(){
        friendsRequests.deleteFriends(loggedinUserLogin,user.getLogin(), deleteFriendsResult);
    }


}
