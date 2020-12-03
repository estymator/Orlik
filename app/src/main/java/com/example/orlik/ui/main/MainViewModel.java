package com.example.orlik.ui.main;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.UserRequests;
import com.example.orlik.data.model.User;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;


public class MainViewModel extends ViewModel {
    private final static String TAG="MainViewModelTAG";
    private MainDataSource mainDataSource = new MainDataSource();
    private MutableLiveData<LogoutResult> logoutResultMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> loggedInUser= new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> friends=new MutableLiveData<>();
    UserRequests userRequests = new UserRequests();
    FriendsRequests friendsRequests = new FriendsRequests();

    /**
     * fill in List of friends
     */
    public String[] getFriendsLogin(){
        String[] friendsLogin = new String[friends.getValue().size()];
        for(int i = 0;i<friends.getValue().size();i++){
            friendsLogin[i]=friends.getValue().get(i).getLogin();
        }
       return friendsLogin;
    }

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

    private AdapterView.OnItemClickListener friendsListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            
            Log.v(TAG, position+" "+id);

        }
    };

    public AdapterView.OnItemClickListener getFriendsListListener(){
        return friendsListListener;
    }
}
