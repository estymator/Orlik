package com.example.orlik.ui.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.AdminRequest;
import com.example.orlik.Network.PitchRequests;
import com.example.orlik.Network.UserRequests;
import com.example.orlik.data.adapters.PitchAdapter;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.User;

import java.util.ArrayList;

public class AdminViewModel extends ViewModel {
    private final static String TAG="AdminViewModelTAG";
    private MutableLiveData<String> fragmentNavigator = new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> findUserResult = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Pitch>> findPitchResult = new MutableLiveData<>();
    private AdminRequest adminRequest = new AdminRequest();
    private UserRequests userRequests = new UserRequests();
    PitchRequests pitchRequests = new PitchRequests();

    public MutableLiveData<String> getFragmentNavigator() {
        return fragmentNavigator;
    }

    public MutableLiveData<ArrayList<User>> getFindUserResult() {
        return findUserResult;
    }


    public MutableLiveData<ArrayList<Pitch>> getFindPitchResult() {
        return findPitchResult;
    }

    public void searchUsers(String query){
        userRequests.findUsers(query, findUserResult);
    }

    public void searchPitch(String query){
        pitchRequests.findPitchRequest(query, findPitchResult);
    }

}
