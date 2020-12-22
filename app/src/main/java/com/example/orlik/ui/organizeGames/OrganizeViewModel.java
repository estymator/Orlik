package com.example.orlik.ui.organizeGames;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;

public class OrganizeViewModel extends ViewModel {
    private Context context;
    private LocationGetter locationGetter;
    private MutableLiveData<String> fragmentNavigator = new MutableLiveData<>("");
    private final static String TAG="OrganizeViewModelTAG";
    private User loggedInUser;
    private int maxPlayersNumber=12, minPlayersNumber=12, pitchId=1;
    private String gameDate="", visibility="public";
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




    public void getLocalization(Context context){
        locationGetter = new LocationGetter(context);
        Log.v(TAG,locationGetter.getLat()+" "+locationGetter.getLon());
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

}
