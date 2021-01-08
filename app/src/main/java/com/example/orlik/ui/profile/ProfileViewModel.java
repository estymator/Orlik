package com.example.orlik.ui.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.AdminRequest;
import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.GameRequests;
import com.example.orlik.Network.NotificationRequests;
import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {
    private final static String TAG="ProfileViewModelTAG";
    private User user;
    private String loggedinUserLogin;
    private Session session;
    private MutableLiveData<ArrayList<User>> friends = new MutableLiveData<>();
    private MutableLiveData<ArrayList<GameDTO>> organisedGames = new MutableLiveData<>();
    private MutableLiveData<ArrayList<GameDTO>> attendGames = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteFriendsResult = new MutableLiveData<>();
    private MutableLiveData<Notification> addFriendsResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteUserResult = new MutableLiveData<>();
    private MutableLiveData<Friends> confirmFriendsResult = new MutableLiveData<>();
    private MutableLiveData<User> blockUserResult = new MutableLiveData<>();
    private MutableLiveData<User> unbanUserResult = new MutableLiveData<>();
    private MutableLiveData<Notification> findNotificationResult = new MutableLiveData<>();
    private Boolean sendIvitation = new Boolean(false);
    private boolean isFriend;
    private boolean isAdmin=false;
    private boolean isBlocked = false;
    private FriendsRequests friendsRequests = new FriendsRequests();
    private GameRequests gameRequests = new GameRequests();
    private AdminRequest adminRequest = new AdminRequest();
    private NotificationRequests notificationRequests = new NotificationRequests();

    public String getLoggedinUserLogin() {
        return loggedinUserLogin;
    }

    public void setLoggedinUserLogin(String loggedinUserLogin) {
        this.loggedinUserLogin = loggedinUserLogin;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setSession(Context context){
        session = new Session(context);
        if(session.getRole().equals("ROLE_ADMIN")){
            this.isAdmin=true;
        }
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBlocked() {
        return isBlocked;
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

    public MutableLiveData<ArrayList<GameDTO>> getOrganisedGames(){
        return organisedGames;
    }

    public MutableLiveData<ArrayList<GameDTO>> getAttendGames() {
        return attendGames;
    }

    public MutableLiveData<Boolean> getDeleteFriendsResult() {
        return deleteFriendsResult;
    }


    public MutableLiveData<Notification> getAddFriendsResult() {
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
        notificationRequests.addNotificationRequest(loggedinUserLogin, user.getLogin(),1, addFriendsResult);
    }

    public void checkFriendInvitation(String destinationLogin){
        notificationRequests.findNotificationRequest(loggedinUserLogin,destinationLogin, 1, findNotificationResult);
        notificationRequests.findNotificationRequest(destinationLogin, loggedinUserLogin,1, findNotificationResult);
    }

    public void deleteFriends(){
        friendsRequests.deleteFriends(loggedinUserLogin,user.getLogin(), deleteFriendsResult);
    }

    public void deleteUser(){
        adminRequest.deleteUserRequest(user.getLogin(), deleteUserResult);
    }

    public void blockUser(){
        adminRequest.blockUserRequest(user.getLogin(), blockUserResult);
    }

    public void unbanUser(){
        adminRequest.unbanUserRequest(user.getLogin(), unbanUserResult);
    }

    public MutableLiveData<Boolean> getDeleteUserResult() {
        return deleteUserResult;
    }

    public MutableLiveData<User> getBlockUserResult() {
        return blockUserResult;
    }

    public MutableLiveData<User> getUnbanUserResult() {
        return unbanUserResult;
    }

    public MutableLiveData<Notification> getFindNotificationResult() {
        return findNotificationResult;
    }

    public void setSendIvitation(Boolean sendIvitation) {
        this.sendIvitation = sendIvitation;
    }

    public Boolean getSendIvitation() {
        return sendIvitation;
    }

    public MutableLiveData<Friends> getConfirmFriendsResult() {
        return confirmFriendsResult;
    }


    public void confirmFriends(){
        friendsRequests.addFriends(loggedinUserLogin, user.getLogin(), confirmFriendsResult);
    }
}
