package com.example.orlik.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.User;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {
    private final static String TAG="ProfileViewModelTAG";
    private User user;
    private String loggedinUserLogin;
    private MutableLiveData<ArrayList<User>> friends = new MutableLiveData<>();
    private MutableLiveData<Game> organisedGames = new MutableLiveData<>();
    private MutableLiveData<Game> attendGames = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteFriendsResult = new MutableLiveData<>();
    private MutableLiveData<Friends> addFriendsResult = new MutableLiveData<>();
    private boolean isFriend;
    FriendsRequests friendsRequests = new FriendsRequests();

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

    public void setFriends(MutableLiveData<ArrayList<User>> friends) {
        this.friends = friends;
    }

    public void setOrganisedGames(MutableLiveData<Game> organisedGames) {
        this.organisedGames = organisedGames;
    }

    public void setAttendGames(MutableLiveData<Game> attendGames) {
        this.attendGames = attendGames;
    }

    public MutableLiveData<Boolean> getDeleteFriendsResult() {
        return deleteFriendsResult;
    }

    public void setDeleteFriendsResult(MutableLiveData<Boolean> deleteFriendsResult) {
        this.deleteFriendsResult = deleteFriendsResult;
    }

    public MutableLiveData<Friends> getAddFriendsResult() {
        return addFriendsResult;
    }

    public void setAddFriendsResult(MutableLiveData<Friends> addFriendsResult) {
        this.addFriendsResult = addFriendsResult;
    }

    public void getFriendsOfUser(){
        friendsRequests.loadFriends(user.getLogin(),friends);
    }

    public void getOrganisedGames(){

    }

    public void getAttendGames(){

    }

    public void addFriends(){
        friendsRequests.addFriends(loggedinUserLogin, user.getLogin(), addFriendsResult);
    }

    public void deleteFriends(){
        friendsRequests.deleteFriends(loggedinUserLogin,user.getLogin(), deleteFriendsResult);
    }
}
