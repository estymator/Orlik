package com.example.orlik.ui.stats;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.GameRequests;
import com.example.orlik.data.adapters.GamesResultAdapter;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;

import java.util.ArrayList;

public class StatsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<GameDTO>> getCheckedGamesResult = new MutableLiveData<>();
    private MutableLiveData<ArrayList<GameDTO>> getFinishedGamesResult = new MutableLiveData<>();
    private MutableLiveData<String> fragmentNavigator = new MutableLiveData<>();
    private GameRequests gameRequests = new GameRequests();
    private User user;
    private ArrayList<GameDTO> checkedAdapterDataSet= new ArrayList<>();
    private ArrayList<GameDTO> finishedAdapterDataSet = new ArrayList<>();
    private GamesResultAdapter checkedAdapter = new GamesResultAdapter(checkedAdapterDataSet);
    private GamesResultAdapter finishedAdapter = new GamesResultAdapter(finishedAdapterDataSet);

    public User getUser(){
        return user;
    }

    public void setUser(Session sesja){
        user = sesja.getUser();
    }

    public MutableLiveData<ArrayList<GameDTO>> getGetCheckedGamesResult() {
        return getCheckedGamesResult;
    }

    public MutableLiveData<ArrayList<GameDTO>> getGetFinishedGamesResult() {
        return getFinishedGamesResult;
    }

    public ArrayList<GameDTO> getCheckedAdapterDataSet() {
        return checkedAdapterDataSet;
    }

    public ArrayList<GameDTO> getFinishedAdapterDataSet() {
        return finishedAdapterDataSet;
    }

    public GamesResultAdapter getCheckedAdapter() {
        return checkedAdapter;
    }

    public GamesResultAdapter getFinishedAdapter() {
        return finishedAdapter;
    }

    public MutableLiveData<String> getFragmentNavigator() {
        return fragmentNavigator;
    }

    public void getListOfGames(){
        gameRequests.loadCheckedGames(user.getLogin(), getCheckedGamesResult);
        gameRequests.loadFinishedGames(user.getLogin(), getFinishedGamesResult);
    }
}
