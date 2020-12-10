package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRequests {
    private final static String TAG="GameRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<ArrayList<Game>> gamesList;
    public void loadGames(double lat, double lon, int range, String type, MutableLiveData<ArrayList<Game>> gamesList){
        this.gamesList=gamesList;
        Log.v(TAG, "lat  = "+lat+" lon = "+lon);
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<Game>> getGamesCall = serverAPI.getGames(lat,lon,range,type);

            getGamesCall.enqueue(new Callback<ArrayList<Game>>(){
                @Override
                public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                    Log.v(TAG,"OnResponse");
                    if(response.isSuccessful()){
                        Log.v(TAG,"ResponseSuccessful");

                        if(response.body()!=null) {
                            ArrayList<Game> result= new ArrayList();
                            try{
                                result =(ArrayList<Game>) response.body();
                            }catch (Throwable t){
                                getGamesFailure(t.getMessage());
                            }
                            getGamesSuccess(result);
                        }else
                        {
                            getGamesFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getGamesFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Game>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getGamesFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }

    }


    private void getGamesSuccess(ArrayList<Game> result){
        this.gamesList.setValue(result);
    }

    private void getGamesFailure(String mess){
        Log.v(TAG, mess);
        this.gamesList.setValue(new ArrayList<Game>());
    }
}
