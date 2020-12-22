package com.example.orlik.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.Network.NetworkError;
import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.Network.ServerAPI;
import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDataSource {
    private final static String TAG="MainDataSourceTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<LogoutResult> logoutResultMutableLiveData = new MutableLiveData<>();
    public void logout(MutableLiveData<LogoutResult> logoutResultMutableLiveData){
        this.logoutResultMutableLiveData=logoutResultMutableLiveData;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try {


            Call<Object> logoutCall = serverAPI.logoutUser();

            logoutCall.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        Log.v(TAG,"Wylogowano");
                        RetrofitServiceGenerator.clearCredentials();
                        logoutSuccessful();

                    } else {
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        logoutError();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                    Log.v(TAG,t.getMessage());
                    logoutError();
                }

            });

        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }

    }
    public void listOfUsers(){
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try {

            Call<List<User>> getUsersCall = serverAPI.getAllUsers();
            getUsersCall.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        Log.v(TAG,"Pobrano liste");

                    } else {
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.v(TAG,t.getMessage());
                }

            });

        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }
    }
    private void logoutSuccessful(){
        if(this.logoutResultMutableLiveData!=null){
            this.logoutResultMutableLiveData.setValue(new LogoutResult("Wylogowano"));

        }
    }

    private void logoutError(){
        if(this.logoutResultMutableLiveData!=null){
            this.logoutResultMutableLiveData.setValue(new LogoutResult("Błąd wylogowania"));

        }
    }
}
