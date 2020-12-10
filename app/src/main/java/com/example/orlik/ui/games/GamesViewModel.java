package com.example.orlik.ui.games;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.GameRequests;
import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Pitch;

import java.util.ArrayList;

public class GamesViewModel extends ViewModel{
    private final static String TAG = "GamesViewModelTAG";
    private MutableLiveData<ArrayList<Pitch>> searchResult;
    private LocationGetter locationGetter;
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Game>> gamesList = new MutableLiveData<>();
    private String pitchItem;
    private String rangeItem;
    private GameRequests gameRequests = new GameRequests();

    public MutableLiveData<ArrayList<Pitch>> getSearchResult() {
        return searchResult;
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    public MutableLiveData<ArrayList<Game>> getGamesList() {
        return gamesList;
    }

    public void searchGames(String pitchChoose, String rangeChoose, Context context){
        double lat= locationGetter.getLat();
        double lon = locationGetter.getLon();
        Integer rangeInt=10;
        try{
            rangeInt = Integer.parseInt(rangeChoose.split("km")[0]);
        }catch (Exception e)
        {
            Log.v(TAG, e.getMessage());
        }
        gameRequests.loadGames(lat,lon, rangeInt, pitchChoose, gamesList);
    }

    public void getLocalization(Context context)
    {
        locationGetter = new LocationGetter(context);
        address.setValue(locationGetter.getLocation());
    }

    public void setOrganizeSpinner(Spinner s, int array, Context context){
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(spinnerAdapter);
    }
}
