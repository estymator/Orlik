package com.example.orlik.ui.main;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainViewModel extends ViewModel {
    private MainDataSource mainDataSource = new MainDataSource();
    private MutableLiveData<LogoutResult> logoutResultMutableLiveData = new MutableLiveData<>();

    public void logout(){
        mainDataSource.logout(logoutResultMutableLiveData);
    }

    public void listUsers(){
        mainDataSource.listOfUsers();
    }

    public LiveData<LogoutResult> getLogoutResult(){
        return logoutResultMutableLiveData;
    }

}
