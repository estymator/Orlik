package com.example.orlik.ui.organizeGames;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.PitchRequests;
import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;

import java.util.ArrayList;

public class OrganizeViewModel extends ViewModel {
    private final static String TAG="OrganizeViewModelTAG";
    private Context context;
    private LocationGetter locationGetter;
    private PitchRequests pitchRequests = new PitchRequests();
    private MutableLiveData<String> fragmentNavigator = new MutableLiveData<>("");

//    Data for addGameFragment
    private MutableLiveData<ArrayList<Pitch>> pitchList= new MutableLiveData<>();
    private User loggedInUser;
    private int maxPlayersNumber=12, minPlayersNumber=12, pitchId=1;
    private String gameDate="", gameTime="", visibility="public";
    private boolean organizerPlay=false;



    public void setContext(Context c)
    {
        this.context=c;
        locationGetter = new LocationGetter(context);
        Session session = new Session(this.context);
        try{
            loggedInUser=session.getUser();   //TODO : add logic to not loaded case
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public boolean isOrganizerPlay() {
        return organizerPlay;
    }

    public void setOrganizerPlay(boolean organizerPlay) {
        this.organizerPlay = organizerPlay;
    }

    public void setMaxPlayersNumber(int maxPlayersNumber) {
        this.maxPlayersNumber = maxPlayersNumber;
    }

    public void setMinPlayersNumber(int minPlayersNumber) {
        this.minPlayersNumber = minPlayersNumber;
    }

    public void setPitchId(int pitchId) {
        this.pitchId = pitchId;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public void setVisibility(String visibility){
        this.visibility=visibility;
    }

    public void getLocalization(Context context){
        locationGetter = new LocationGetter(context);
        Log.v(TAG,locationGetter.getLat()+" "+locationGetter.getLon());
    }

    public MutableLiveData<ArrayList<Pitch>> getPitchList() {
        return pitchList;
    }

    public MutableLiveData<String> getFragmentNavigator() {
        return fragmentNavigator;
    }

    public void setOrganizeSpinner(Spinner s, int array){
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(spinnerAdapter);
    }

    public void requestPitchList(){
        pitchRequests.getPitchInGivenRange(locationGetter.getLat(),locationGetter.getLon(), 15, pitchList);
    }

    public void setPitchesAdapter(Spinner s){
        if(this.pitchList.getValue().size()>0){
            ArrayAdapter spinnerAdapter =new ArrayAdapter(context,
                    android.R.layout.simple_spinner_item, this.pitchList.getValue().toArray());
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(spinnerAdapter);
        }
    }


    public void addGame(){

    }

}
