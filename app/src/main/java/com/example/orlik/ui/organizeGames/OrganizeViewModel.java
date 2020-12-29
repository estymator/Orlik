package com.example.orlik.ui.organizeGames;

import android.content.Context;
import android.icu.util.EthiopicCalendar;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.FriendsRequests;
import com.example.orlik.Network.GameRequests;
import com.example.orlik.Network.PitchRequests;
import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.organizeGames.formsState.AddGameFormState;

import java.util.ArrayList;

public class OrganizeViewModel extends ViewModel {
    private final static String TAG="OrganizeViewModelTAG";
    private Context context;
    private LocationGetter locationGetter;
    private PitchRequests pitchRequests = new PitchRequests();
    private GameRequests gameRequests = new GameRequests();
    private MutableLiveData<String> fragmentNavigator = new MutableLiveData<>("");
    private MutableLiveData<AddGameFormState> addGameState = new MutableLiveData<>();

//    Data for addGameFragment
    private MutableLiveData<ArrayList<Pitch>> pitchList= new MutableLiveData<>();
    private MutableLiveData<Game> addGameResult= new MutableLiveData<>();
    private User loggedInUser;
    private int maxPlayersNumber=12, minPlayersNumber=12, pitchId=1;
    private String gameDate="", gameTime="", visibility="public";
    private boolean organizerPlay=false;
    private String description="";
    private Integer duration=90;

    //Data for addPitchFragment
    private MutableLiveData<Pitch> addPitchResult = new MutableLiveData<>();
    private String pitchAddress="";
    private double pitchLon=0,pitchLat=0;
    private String pitchType="Dowolny";



    public void setContext(Context c)
    {
        this.context=c;
        locationGetter = new LocationGetter(context);
        locationGetter.getLocation();
        Session session = new Session(this.context);
        try{
            loggedInUser=session.getUser();   //TODO : add logic to not loaded case
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setPitchAddressAndLocalization(String pitchAddress) {
        this.pitchAddress = pitchAddress;
        this.pitchLat=locationGetter.getLat();
        this.pitchLon=locationGetter.getLon();
    }

    public void setPitchAddressAndLocalization(String pitchAddress, double lat, double lon) {
        this.pitchAddress = pitchAddress;
        this.pitchLat=lat;
        this.pitchLon=lon;
    }

    public String getPitchAddress() {
        return pitchAddress;
    }

    public void setPitchLon(double pitchLon) {
        this.pitchLon = pitchLon;
    }

    public void setPitchLat(double pitchLat) {
        this.pitchLat = pitchLat;
    }

    public void setPitchType(String pitchType) {
        this.pitchType = pitchType;
    }

    public MutableLiveData<Pitch> getAddPitchResult() {
        return addPitchResult;
    }

    public void addPitch(){
        String location=pitchLat+":"+pitchLon;
        pitchRequests.addPitch(location, this.pitchAddress, this.pitchType, this.addPitchResult);
        Log.v(TAG, pitchType+" "+pitchLat+":"+pitchLon+" "+pitchAddress);
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addGameFormDataChanged(String description, String duration){
        Integer durationInt=90;
        try{
            durationInt=Integer.valueOf(duration);
        }catch (Exception e){
            this.addGameState.setValue(new AddGameFormState(false, true, false));
            return;
        }
        if(description.length()>30){
            this.addGameState.setValue(new AddGameFormState(true, false, false));
        }else if(durationInt<0||durationInt>300){
            this.addGameState.setValue(new AddGameFormState(false, true, false));
        }else{
            this.addGameState.setValue(new AddGameFormState(false,false, true));
        }
    }

    public MutableLiveData<AddGameFormState> getAddGameState() {
        return addGameState;
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

    public void setPitchId(int indeks) {
        if(this.pitchList.getValue().size()>indeks){
            this.pitchId = this.pitchList.getValue().get(indeks).getPitchId();
        }
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

   public String getAddress(double lat, double lon){
        return locationGetter.getAddress(lat, lon);
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

    public double getLatitude(){
        return  locationGetter.getLat();
    }

    public double getLongitude(){
        return locationGetter.getLon();
    }

    public String getLocation(){
        return locationGetter.getLocation();
    }

    public void requestPitchList(){
        pitchRequests.getPitchInGivenRange(locationGetter.getLat(),locationGetter.getLon(), 50, pitchList);
    }

    public void setPitchesAdapter(Spinner s){
        if(this.pitchList.getValue().size()>0){
            ArrayList<String> pitchSpinnerList= new ArrayList<>();
            for(int i=0;i<this.pitchList.getValue().size();i++){
                Pitch bufor=this.pitchList.getValue().get(i);
                pitchSpinnerList.add(bufor.getType()+" "+bufor.getAdress());
            }
            ArrayAdapter spinnerAdapter =new ArrayAdapter(context,
                    android.R.layout.simple_spinner_item, pitchSpinnerList.toArray());
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(spinnerAdapter);
        }
    }

    public MutableLiveData<Game> getAddGameResult() {
        return addGameResult;
    }

    public void addGame(){
        int visibilityInt=(this.visibility.equals("Publiczny") ? 1 : 0);
        gameRequests.addGame(loggedInUser.getLogin(), pitchId, maxPlayersNumber, minPlayersNumber, visibilityInt, (gameTime+" "+gameDate), description, duration, organizerPlay,addGameResult);//TODO add description login
    }

}
