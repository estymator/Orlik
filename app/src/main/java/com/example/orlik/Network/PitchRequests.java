package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.User;

import java.util.ArrayList;

public class PitchRequests {
    public final static String TAG = "PitchRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<ArrayList<Pitch>> pitchList;
    private void getPitchSuccesfully(){

    }

    private void getPitchFailure(String mess){
        Log.v(TAG,mess);

    }
}
