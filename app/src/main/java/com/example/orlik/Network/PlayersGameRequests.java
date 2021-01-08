package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.PlayersGame;
import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersGameRequests {
    private static final String TAG = "PlayersGameRequests";
    private ServerAPI serverAPI;
    private MutableLiveData<ArrayList<String>> getPlayersResult;
    private MutableLiveData<Boolean> optOutPlayerResult;
    private MutableLiveData<PlayersGame> addPlayerToGameResult;

    public void loadPlayers(Integer gameId, MutableLiveData<ArrayList<String>> getPlayersResult){
        this.getPlayersResult=getPlayersResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<String>> getPlayersOfGameCall = serverAPI.getPlayersOfGame(gameId);

            getPlayersOfGameCall.enqueue(new Callback<ArrayList<String>>(){
                @Override
                public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            ArrayList<String> result= new ArrayList();
                            try{
                                result = (ArrayList<String>) response.body();
                            }catch (Throwable t){
                                getPlayersOfGameFailure(t.getMessage());
                            }
                            getPlayersOfGameSuccess(result);
                        }else
                        {
                            getPlayersOfGameFailure("Bład pobrania danych");
                        }
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getPlayersOfGameFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    getPlayersOfGameFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            getPlayersOfGameFailure(e.getMessage());
        }

    }

    private void getPlayersOfGameSuccess(ArrayList<String> result){
        this.getPlayersResult.setValue(result);
    }
    private void getPlayersOfGameFailure(String mess){
        Log.v(TAG, mess);
        this.getPlayersResult.setValue(new ArrayList<String>());
    }

    public void optOutPlayers(Integer gameId, String login,  MutableLiveData<Boolean> optOutPlayerResult){
        this.optOutPlayerResult=optOutPlayerResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Boolean> optOutPlayerCall = serverAPI.optOutPlayer(gameId, login);

            optOutPlayerCall.enqueue(new Callback<Boolean>(){
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            Boolean result= new Boolean(false);
                            try{
                                result = (Boolean) response.body();
                            }catch (Throwable t){
                                optOutPlayerFailure(t.getMessage());
                            }
                            optOutPlayerSuccess(result);
                        }else
                        {
                            optOutPlayerFailure("Bład pobrania danych");
                        }
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        optOutPlayerFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    optOutPlayerFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            optOutPlayerFailure(e.getMessage());
        }

    }

    private void optOutPlayerSuccess(Boolean result){
        this.optOutPlayerResult.setValue(result);
    }
    private void optOutPlayerFailure(String mess){
        Log.v(TAG, mess);
        this.optOutPlayerResult.setValue(new Boolean(false));
    }

    public void sigUpPlayer(Integer gameId, String login, int team, MutableLiveData<PlayersGame> addPlayerToGameResult){
        this.addPlayerToGameResult=addPlayerToGameResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<PlayersGame> signUpPlayerCall = serverAPI.addPlayerToGame(gameId, login, team);

            signUpPlayerCall.enqueue(new Callback<PlayersGame>(){
                @Override
                public void onResponse(Call<PlayersGame> call, Response<PlayersGame> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            PlayersGame result= new PlayersGame();
                            try{
                                result = (PlayersGame) response.body();
                            }catch (Throwable t){
                                signUpPlayerFailure(t.getMessage());
                            }
                            signUpPlayerSuccess(result);
                        }else
                        {
                            signUpPlayerFailure("Bład pobrania danych");
                        }
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        signUpPlayerFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<PlayersGame> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    signUpPlayerFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            signUpPlayerFailure(e.getMessage());
        }

    }

    private void signUpPlayerSuccess(PlayersGame result){
        this.addPlayerToGameResult.setValue(result);
    }
    private void signUpPlayerFailure(String mess){
        Log.v(TAG, mess);
        this.addPlayerToGameResult.setValue(new PlayersGame());
    }
}
