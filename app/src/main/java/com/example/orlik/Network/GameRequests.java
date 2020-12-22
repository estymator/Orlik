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
    private MutableLiveData<ArrayList<Game>> gamesList, organisedGames, attendGames;
    public void loadGames(double lat, double lon, int range, String type, MutableLiveData<ArrayList<Game>> gamesList){
        this.gamesList=gamesList;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<Game>> getGamesCall = serverAPI.getGames(lat,lon,range,type);
            getGamesCall.enqueue(new Callback<ArrayList<Game>>(){
                @Override
                public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                    if(response.isSuccessful()){

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

    public void loadOrganisedGames(String organiserLogin, MutableLiveData<ArrayList<Game>> organisedGames){
        this.organisedGames=organisedGames;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<Game>> getGamesByOrganiserCall = serverAPI.getGamesByOrganiserLogin(organiserLogin);
            getGamesByOrganiserCall.enqueue(new Callback<ArrayList<Game>>(){
                @Override
                public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<Game> result= new ArrayList();
                            try{
                                result =(ArrayList<Game>) response.body();
                            }catch (Throwable t){
                                getOrganizedGamesFailure(t.getMessage());
                            }
                            getOrganizedGamesSuccess(result);
                        }else
                        {
                            getOrganizedGamesFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getOrganizedGamesFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Game>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getOrganizedGamesFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            getOrganizedGamesFailure(e.getMessage());
        }
    }

    private void getOrganizedGamesSuccess(ArrayList<Game> result){
        this.organisedGames.setValue(result);
    }

    private void getOrganizedGamesFailure(String mess){
        Log.v(TAG, mess);
    }

    public void loadAttendGames(String playerLogin, MutableLiveData<ArrayList<Game>> attendGames){
        this.attendGames=attendGames;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<Game>> getAttendGamesCall = serverAPI.getGamesByPlayerLogin(playerLogin);

            getAttendGamesCall.enqueue(new Callback<ArrayList<Game>>(){
                @Override
                public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<Game> result= new ArrayList();
                            try{
                                result =(ArrayList<Game>) response.body();
                            }catch (Throwable t){
                                getGamesFailure(t.getMessage());
                            }
                            getAttendGamesSuccess(result);
                        }else
                        {
                            getAttendGamesFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getAttendGamesFailure(message);
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
            getAttendGamesFailure(e.getMessage());
        }
    }

    private void getAttendGamesSuccess(ArrayList<Game> result){
        this.attendGames.setValue(result);
    }

    private void getAttendGamesFailure(String mess){
        Log.v(TAG, mess);
    }
}
