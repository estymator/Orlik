package com.example.orlik.ui.main;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.GameRequests;
import com.example.orlik.Network.NotificationRequests;
import com.example.orlik.Network.UserRequests;
import com.example.orlik.data.adapters.FriendsAdapter;
import com.example.orlik.data.adapters.GamesResultAdapter;
import com.example.orlik.data.adapters.NotificationAdapter;
import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.data.model.dto.NotificationDTO;

import java.util.ArrayList;


public class MainViewModel extends ViewModel {
    private final static String TAG="MainViewModelTAG";

    private MainDataSource mainDataSource = new MainDataSource();
    private MutableLiveData<ArrayList<User>> searchUserResult = new MutableLiveData<>(new ArrayList<User>());
    private MutableLiveData<LogoutResult> logoutResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> loggedInUser= new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> friends=new MutableLiveData<>(new ArrayList<User>());
    private MutableLiveData<ArrayList<GameDTO>> getOrganisedGameResult = new MutableLiveData<>(new ArrayList<GameDTO>());
    private MutableLiveData<ArrayList<GameDTO>> getAttendGameResult = new MutableLiveData<>(new ArrayList<GameDTO>());
    private MutableLiveData<ArrayList<NotificationDTO>> notificationsResult = new MutableLiveData<>(new ArrayList<NotificationDTO>());
    private MutableLiveData<Boolean> deleteNotificationResult = new MutableLiveData<>(false);
    private UserRequests userRequests = new UserRequests();
    private GameRequests gameRequests = new GameRequests();
    private NotificationRequests notificationRequests = new NotificationRequests();
    private FriendsRequests friendsRequests = new FriendsRequests();
    private ArrayList<User> friendsAdapterDataSet = new ArrayList<>();
    private ArrayList<User> searchUsersAdapterDataSet = new ArrayList<>();
    private ArrayList<GameDTO> attendGamesAdapterDataSet= new ArrayList<>();
    private ArrayList<GameDTO> organizeGamesAdapterDataSet= new ArrayList<>();
    private ArrayList<NotificationDTO> notificationsDataSet= new ArrayList<>();

    private FriendsAdapter friendsAdapter = new FriendsAdapter(friendsAdapterDataSet, true);
    private FriendsAdapter searchUsersAdapter = new FriendsAdapter(searchUsersAdapterDataSet, false);
    private GamesResultAdapter attendGamesAdapter = new GamesResultAdapter(attendGamesAdapterDataSet);
    private GamesResultAdapter organizeGamesAdapter = new GamesResultAdapter(organizeGamesAdapterDataSet);
    private NotificationAdapter notificationAdapter = new NotificationAdapter(notificationsDataSet);


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

    public ArrayList<GameDTO> getAttendGamesAdapterDataSet() {
        return attendGamesAdapterDataSet;
    }

    public LiveData<LogoutResult> getLogoutResult(){
        return logoutResultMutableLiveData;
    }

    public MutableLiveData<ArrayList<User>> getFriends() {
        return friends;
    }


    public MutableLiveData<ArrayList<User>> getSearchUserResult() {
        return searchUserResult;
    }

    public ArrayList<GameDTO> getOrganizeGamesAdapterDataSet() {
        return organizeGamesAdapterDataSet;
    }

    public FriendsAdapter getFriendsAdapter() {
        return friendsAdapter;
    }

    public FriendsAdapter getSearchUsersAdapter() {
        return searchUsersAdapter;
    }

    public GamesResultAdapter getAttendGamesAdapter() {
        return attendGamesAdapter;
    }

    public GamesResultAdapter getOrganizeGamesAdapter() {
        return organizeGamesAdapter;
    }

    public MutableLiveData<ArrayList<GameDTO>> getGetOrganisedGameResult() {
        return getOrganisedGameResult;
    }

    public MutableLiveData<ArrayList<GameDTO>> getGetAttendGameResult() {
        return getAttendGameResult;
    }

    public ArrayList<User> getFriendsAdapterDataSet() {
        return friendsAdapterDataSet;
    }

    public ArrayList<User> getSearchUsersAdapterDataSet() {
        return searchUsersAdapterDataSet;
    }


    public MutableLiveData<ArrayList<NotificationDTO>> getNotificationsResult() {
        return notificationsResult;
    }

    public ArrayList<NotificationDTO> getNotificationsDataSet() {
        return notificationsDataSet;
    }

    public MutableLiveData<Boolean> getDeleteNotificationResult() {
        return deleteNotificationResult;
    }

    public void clearUser(){
        Log.v(TAG,"Clear User");
        loggedInUser.setValue(null);
    }

    public NotificationAdapter getNotificationAdapter() {
        return notificationAdapter;
    }

    public void deleteNotification(int id){
        notificationRequests.deleteNotificationRequest(id, deleteNotificationResult);
    }

    public void findUsers(String query){
        userRequests.findUsers(query, searchUserResult);
    }

    public void loadGames(){
        gameRequests.loadAttendGames(loggedInUser.getValue().getLogin(),getAttendGameResult);
        gameRequests.loadOrganisedGames(loggedInUser.getValue().getLogin(),getOrganisedGameResult);
        notificationRequests.getNotificationRequest(loggedInUser.getValue().getLogin(), notificationsResult);
        friendsRequests.loadFriends(loggedInUser.getValue().getLogin(), this.friends);
    }

    public void loadFriends(){
        friendsRequests.loadFriends(loggedInUser.getValue().getLogin(), this.friends);
    }
    public void reloadNotifications(){
        notificationRequests.getNotificationRequest(loggedInUser.getValue().getLogin(), notificationsResult);
    }
}
