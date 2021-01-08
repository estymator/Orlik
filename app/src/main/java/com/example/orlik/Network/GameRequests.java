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
    private MutableLiveData<ArrayList<GameDTO>> getCheckedGamesResult, getAddStatsGameResult;
    private MutableLiveData<GameDTO> addResult;
    private MutableLiveData<Boolean> deleteGameResult;

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
        this.organisedGames.setValue(new ArrayList<GameDTO>());
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
                    getAttendGamesFailure(t.getMessage());
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
        this.attendGames.setValue(new ArrayList<GameDTO>());
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

    public void loadCheckedGames(String login, MutableLiveData<ArrayList<GameDTO>> checkedGames){
        this.getCheckedGamesResult=checkedGames;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<GameDTO>> getCheckedGamesCall = serverAPI.getGamesChecked(login);
            getCheckedGamesCall.enqueue(new Callback<ArrayList<GameDTO>>(){
                @Override
                public void onResponse(Call<ArrayList<GameDTO>> call, Response<ArrayList<GameDTO>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<GameDTO> result= new ArrayList();
                            try{
                                result =(ArrayList<GameDTO>) response.body();
                            }catch (Throwable t){
                                getCheckedGamesFailure(t.getMessage());
                            }
                            getCheckedGamesSuccess(result);
                        }else
                        {
                            getCheckedGamesFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getCheckedGamesFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<GameDTO>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getCheckedGamesFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            getOrganizedGamesFailure(e.getMessage());
        }
    }

    private void getCheckedGamesSuccess(ArrayList<GameDTO> result){
        this.getCheckedGamesResult.setValue(result);
    }

    private void getCheckedGamesFailure(String mess){
        this.getCheckedGamesResult.setValue(new ArrayList<GameDTO>());
        Log.v(TAG, mess);
    }


    public void loadFinishedGames(String login, MutableLiveData<ArrayList<GameDTO>> addStatsGameResult){
        this.getAddStatsGameResult=addStatsGameResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<GameDTO>> getFinishedGamesCall = serverAPI.getfinischedGames(login);
            getFinishedGamesCall.enqueue(new Callback<ArrayList<GameDTO>>(){
                @Override
                public void onResponse(Call<ArrayList<GameDTO>> call, Response<ArrayList<GameDTO>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<GameDTO> result= new ArrayList();
                            try{
                                result =(ArrayList<GameDTO>) response.body();
                            }catch (Throwable t){
                                getFinishedGamesFailure(t.getMessage());
                            }
                            getFinishedGamesSuccess(result);
                        }else
                        {
                            getFinishedGamesFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getFinishedGamesFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<GameDTO>> call, Throwable t) {
                    Log.v(TAG,"onFailure");
                    getFinishedGamesFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            getFinishedGamesFailure(e.getMessage());
        }
    }

    private void getFinishedGamesSuccess(ArrayList<GameDTO> result){
        this.getAddStatsGameResult.setValue(result);
    }

    private void getFinishedGamesFailure(String mess){
        this.getAddStatsGameResult.setValue(new ArrayList<GameDTO>());
        Log.v(TAG, mess);
    }

    public void deleteGame(int gameId, String login, MutableLiveData<Boolean> deleteGameResult){
        this.deleteGameResult=deleteGameResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Boolean> deleteGameCall = serverAPI.deleteGame(login, gameId);

            deleteGameCall.enqueue(new Callback<Boolean>(){
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            Boolean result= new Boolean(false);
                            try{
                                result =(Boolean) response.body();
                            }catch (Throwable t){
                                deleteGameFailure(t.getMessage());
                            }
                            deleteGameSuccess(result);
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
                        deleteGameFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    deleteGameFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            deleteGameFailure(e.getMessage());
        }
    }

    private void deleteGameSuccess(Boolean result){
        this.deleteGameResult.setValue(result);
    }

    private void deleteGameFailure(String mess){
        Log.v(TAG,mess);
        this.deleteGameResult.setValue(new Boolean(false));
    }

    public void addResultToGame(int gameId, String result, MutableLiveData<GameDTO> addResult){
        this.addResult=addResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<GameDTO> addResultCall = serverAPI.addResult(gameId, result);

            addResultCall.enqueue(new Callback<GameDTO>(){
                @Override
                public void onResponse(Call<GameDTO> call, Response<GameDTO> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            GameDTO result= new GameDTO();
                            try{
                                result =(GameDTO) response.body();
                            }catch (Throwable t){
                                addResultFailure(t.getMessage());
                            }
                            addResultSuccess(result);
                        }else
                        {
                            addResultFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        addResultFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<GameDTO> call, Throwable t) {
                    addResultFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            addResultFailure(e.getMessage());
        }
    }

    private void addResultSuccess(GameDTO result){
        this.addResult.setValue(result);
    }

    private void addResultFailure(String mess){
        Log.v(TAG,mess);
        this.addResult.setValue(null);
    }
}
