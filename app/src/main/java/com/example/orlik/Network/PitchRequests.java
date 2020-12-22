package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Pitch;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PitchRequests {
    public final static String TAG = "PitchRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<ArrayList<Pitch>> pitchList;
    public void getPitchInGivenRange(double lat, double lon, int range, MutableLiveData<ArrayList<Pitch>> pitchList){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.pitchList=pitchList;
        try{
            Call<ArrayList<Pitch>> getPitchInGivenRangeCall = serverAPI.getPitch(lat,lon,range);
            getPitchInGivenRangeCall.enqueue(new Callback<ArrayList<Pitch>>(){
                @Override
                public void onResponse(Call<ArrayList<Pitch>> call, Response<ArrayList<Pitch>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<Pitch> result= new ArrayList();
                            try{
                                result =(ArrayList<Pitch>) response.body();
                            }catch (Throwable t){
                                getPitchFailure(t.getMessage());
                            }
                            getPitchSuccesfully(result);
                        }else
                        {
                            getPitchFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        getPitchFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Pitch>> call, Throwable t) {
                    getPitchFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }

    }
    private void getPitchSuccesfully(ArrayList<Pitch> result){
        this.pitchList.setValue(result);
    }

    private void getPitchFailure(String mess){
        Log.v(TAG,mess);
    }
}
