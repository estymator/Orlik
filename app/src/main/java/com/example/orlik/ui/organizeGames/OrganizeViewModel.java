package com.example.orlik.ui.organizeGames;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.ViewModel;

import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;

public class OrganizeViewModel extends ViewModel {
    private Context context;
    private LocationGetter locationGetter;
    private final static String TAG="OrganizeViewModelTAG";

    public void setContext(Context c)
    {
        this.context=c;
        locationGetter = new LocationGetter(context);
    }
    public void getLocalization(Context context){
        locationGetter = new LocationGetter(context);
        Log.v(TAG,locationGetter.getLat()+" "+locationGetter.getLon());
    }


    public void setOrganizeSpinner(Spinner s, int array){
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(spinnerAdapter);
    }
}
