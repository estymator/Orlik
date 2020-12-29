package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRequests {
    private final static String TAG="GameRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<ArrayList<GameDTO>> gamesList, organisedGames, attendGames;
    private MutableLiveData<Game> addGameResult;

    public void loadGames(double lat, double lon, int range, String type, MutableLiveData<ArrayList<GameDTO>> gamesList){
        this.gamesList=gamesList;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<GameDTO>> getGamesCall = serverAPI.getGames(lat,lon,range,type);
            getGamesCall.enqueue(new Callback<ArrayList<GameDTO>>(){
                @Override
                public void onResponse(Call<ArrayList<GameDTO>> call, Response<ArrayList<GameDTO>> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            ArrayList<GameDTO> result= new ArrayList();
                            try{
                                result =(ArrayList<GameDTO>) response.body();
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
                public void onFailure(Call<ArrayList<GameDTO>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getGamesFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }
    }

    private void getGamesSuccess(ArrayList<GameDTO> result){
        this.gamesList.setValue(result);
    }

    private void getGamesFailure(String mess){
        Log.v(TAG, mess);
        this.gamesList.setValue(new ArrayList<GameDTO>());
    }

    public void loadOrganisedGames(String organiserLogin, MutableLiveData<ArrayList<GameDTO>> organisedGames){
        this.organisedGames=organisedGames;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<GameDTO>> getGamesByOrganiserCall = serverAPI.getGamesByOrganiserLogin(organiserLogin);
            getGamesByOrganiserCall.enqueue(new Callback<ArrayList<GameDTO>>(){
                @Override
                public void onResponse(Call<ArrayList<GameDTO>> call, Response<ArrayList<GameDTO>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<GameDTO> result= new ArrayList();
                            try{
                                result =(ArrayList<GameDTO>) response.body();
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
                public void onFailure(Call<ArrayList<GameDTO>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getOrganizedGamesFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            getOrganizedGamesFailure(e.getMessage());
        }
    }

    private void getOrganizedGamesSuccess(ArrayList<GameDTO> result){
        this.organisedGames.setValue(result);
    }

    private void getOrganizedGamesFailure(String mess){
        Log.v(TAG, mess);
    }

    public void loadAttendGames(String playerLogin, MutableLiveData<ArrayList<GameDTO>> attendGames){
        this.attendGames=attendGames;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<GameDTO>> getAttendGamesCall = serverAPI.getGamesByPlayerLogin(playerLogin);

            getAttendGamesCall.enqueue(new Callback<ArrayList<GameDTO>>(){
                @Override
                public void onResponse(Call<ArrayList<GameDTO>> call, Response<ArrayList<GameDTO>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<GameDTO> result= new ArrayList();
                            try{
                                result =(ArrayList<GameDTO>) response.body();
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
                public void onFailure(Call<ArrayList<GameDTO>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getGamesFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            getAttendGamesFailure(e.getMessage());
        }
    }

    private void getAttendGamesSuccess(ArrayList<GameDTO> result){
        this.attendGames.setValue(result);
    }

    private void getAttendGamesFailure(String mess){
        Log.v(TAG, mess);
    }

    public void addGame(String login, int pitchId, int maxPlayers, int minPlayers, int visibility, String schedule, String description, Integer duration, Boolean isOrganserPlaying, MutableLiveData<Game> addGameResult){
        this.addGameResult=addGameResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Game> addGameCall = serverAPI.addGame(maxPlayers,minPlayers,pitchId,login,visibility,schedule, description, duration, isOrganserPlaying);

            addGameCall.enqueue(new Callback<Game>(){
                @Override
                public void onResponse(Call<Game> call, Response<Game> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            Game result= new Game();
                            try{
                                result =(Game) response.body();
                            }catch (Throwable t){
                                addGameFailure(t.getMessage());
                            }
                            addGameSuccess(result);
                        }else
                        {
                            addGameFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        addGameFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Game> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    addGameFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            addGameFailure(e.getMessage());
        }
    }

    private void addGameSuccess(Game result){
        this.addGameResult.setValue(result);
    }

    private void addGameFailure(String mess){
        Log.v(TAG, mess);
    }
}
