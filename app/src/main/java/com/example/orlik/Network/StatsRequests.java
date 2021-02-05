package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.PlayerStatistics;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsRequests {
    private final static String TAG = "StatsRequestsTAG";
    private MutableLiveData<PlayerStatistics> getStatsResult, addStatsResult;
    private ServerAPI serverAPI;

    public void addStats(String login, Integer gameId, Integer goals, Integer assists, MutableLiveData<PlayerStatistics> addStatsResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.addStatsResult=addStatsResult;
        try{
            Call<PlayerStatistics> addStatsCall = serverAPI.addStats(login, gameId, goals, assists);
            addStatsCall.enqueue(new Callback<PlayerStatistics>(){
                @Override
                public void onResponse(Call<PlayerStatistics> call, Response<PlayerStatistics> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            PlayerStatistics result= new PlayerStatistics();
                            try{
                                result =(PlayerStatistics) response.body();
                            }catch (Throwable t){
                                addStatsFailure(t.getMessage());
                            }
                            addStatsSuccesfully(result);
                        }else
                        {
                            addStatsFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        addStatsFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<PlayerStatistics> call, Throwable t) {
                    addStatsFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            addStatsFailure(e.getMessage());
        }

    }
    private void addStatsSuccesfully(PlayerStatistics result){
        this.addStatsResult.setValue(result);
    }

    private void addStatsFailure(String mess){
        Log.v(TAG,mess);
    }


    public void getStats(String login, Integer gameId, MutableLiveData<PlayerStatistics> getStatsResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.getStatsResult=getStatsResult;
        try{
            Call<PlayerStatistics> getStatsCall = serverAPI.getStats(login, gameId);
            getStatsCall.enqueue(new Callback<PlayerStatistics>(){
                @Override
                public void onResponse(Call<PlayerStatistics> call, Response<PlayerStatistics> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            PlayerStatistics result= new PlayerStatistics();
                            try{
                                result =(PlayerStatistics) response.body();
                            }catch (Throwable t){
                                getStatsFailure(t.getMessage());
                            }
                            getStatsSuccesfully(result);
                        }else
                        {
                            getStatsFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        getStatsFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<PlayerStatistics> call, Throwable t) {
                    getStatsFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            getStatsFailure(e.getMessage());
        }

    }
    private void getStatsSuccesfully(PlayerStatistics result){
        this.getStatsResult.setValue(result);
    }

    private void getStatsFailure(String mess){
        Log.v(TAG,mess);
    }
}
