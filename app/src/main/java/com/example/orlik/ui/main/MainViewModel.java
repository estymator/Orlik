package com.example.orlik.ui.main;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.GameRequests;
import com.example.orlik.Network.UserRequests;
import com.example.orlik.data.adapters.FriendsAdapter;
import com.example.orlik.data.model.User;

import java.util.ArrayList;


public class MainViewModel extends ViewModel {
    private final static String TAG="MainViewModelTAG";

    private MainDataSource mainDataSource = new MainDataSource();
    private MutableLiveData<ArrayList<User>> searchUserResult = new MutableLiveData<>(new ArrayList<User>());
    private MutableLiveData<LogoutResult> logoutResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> loggedInUser= new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> friends=new MutableLiveData<>(new ArrayList<User>());
    private UserRequests userRequests = new UserRequests();
    private GameRequests gameRequests = new GameRequests();
    private FriendsRequests friendsRequests = new FriendsRequests();
    private ArrayList<User> friendsAdapterDataSet = new ArrayList<>();
    private ArrayList<User> searchUsersAdapterDataSet = new ArrayList<>();

    private FriendsAdapter friendsAdapter = new FriendsAdapter(friendsAdapterDataSet, true);
    private FriendsAdapter searchUsersAdapter = new FriendsAdapter(searchUsersAdapterDataSet, false);


    public void logout(){
        mainDataSource.logout(logoutResultMutableLiveData);
    }

    public void getUserInfo(){
        userRequests.getLoggedInUser(loggedInUser);
    }

    public MutableLiveData<User> getLoggedInUser(){
        return loggedInUser;
    };

    public void listUsers(){
        mainDataSource.listOfUsers();
    }

    public LiveData<LogoutResult> getLogoutResult(){
        return logoutResultMutableLiveData;
    }

    public MutableLiveData<ArrayList<User>> getFriends() {
        return friends;
    }

    public void loadFriends(){
        friendsRequests.loadFriends(loggedInUser.getValue().getLogin(), this.friends);
    }

    public MutableLiveData<ArrayList<User>> getSearchUserResult() {
        return searchUserResult;
    }

    public FriendsAdapter getFriendsAdapter() {
        return friendsAdapter;
    }

    public FriendsAdapter getSearchUsersAdapter() {
        return searchUsersAdapter;
    }

    public ArrayList<User> getFriendsAdapterDataSet() {
        return friendsAdapterDataSet;
    }

    public ArrayList<User> getSearchUsersAdapterDataSet() {
        return searchUsersAdapterDataSet;
    }

    public void clearUser(){
        Log.v(TAG,"Clear User");
        loggedInUser.setValue(null);
    }

    public void findUsers(String query){
        userRequests.findUsers(query, searchUserResult);
    }
}
