package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRequest {
    private final static String TAG="AdminRequestTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<User> blockUserResult;
    private MutableLiveData<User> unbanUserResult;
    private MutableLiveData<Boolean> removeUserResult;
    private MutableLiveData<Boolean> removePitchResult;
    private MutableLiveData<Pitch> verifyPitchResult;

    public void blockUserRequest(String login, MutableLiveData<User> blockUserResult){
        this.blockUserResult=blockUserResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<User> blockUserCall = serverAPI.blockUser(login);

            blockUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        User result = new User();
                        try {
                            result = (User) response.body();
                            Log.v(TAG, response.body().isValid()+" ");
                        }catch (Exception e){
                            blockUserFailure(e.getMessage());
                        }
                        blockUserSuccessfully(result);
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        blockUserFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    blockUserFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            blockUserFailure(e.getMessage());
        }
    }

    private void blockUserSuccessfully(User result){
        blockUserResult.setValue(result);
    }

    private void blockUserFailure(String message){
        Log.v(TAG, message);
        blockUserResult.setValue(null);
    }


    public void unbanUserRequest(String login, MutableLiveData<User> unbanUserResult){
        this.unbanUserResult=unbanUserResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<User> unbanUserCall = serverAPI.unbanUser(login);

            unbanUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        User result = new User();
                        try {
                            result = (User) response.body();
                        }catch (Exception e){
                            unbanUserFailure(e.getMessage());
                        }
                        unbanUserSuccessfully(result);
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        unbanUserFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    unbanUserFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            unbanUserFailure(e.getMessage());
        }
    }

    private void unbanUserSuccessfully(User result){
        unbanUserResult.setValue(result);
    }

    private void unbanUserFailure(String message){
        Log.v(TAG, message);
        unbanUserResult.setValue(null);
    }

    public void deleteUserRequest(String login, MutableLiveData<Boolean> removeUserResult){
        this.removeUserResult=removeUserResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Boolean> deleteUserCall = serverAPI.deleteUser(login);

            deleteUserCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        Boolean result = new Boolean(false);
                        try {
                            result = (Boolean) response.body();
                        }catch (Exception e){
                            deleteUserFailure(e.getMessage());
                        }
                        deleteUserSuccessfully(result);
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        deleteUserFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    deleteUserFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            deleteUserFailure(e.getMessage());
        }
    }

    private void deleteUserSuccessfully(Boolean result){
        removeUserResult.setValue(result);
    }

    private void deleteUserFailure(String message){
        Log.v(TAG, message);
        removeUserResult.setValue(null);
    }

    public void deletePitchRequest(Integer pitchId, MutableLiveData<Boolean> removePitchResult){
        this.removePitchResult=removePitchResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Boolean> deletePitchCall = serverAPI.deletePitch(pitchId);

            deletePitchCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        Boolean result = new Boolean(false);
                        try {
                            result = (Boolean) response.body();
                        }catch (Exception e){
                            deletePitchFailure(e.getMessage());
                        }
                        deletePitchSuccessfully(result);
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        deletePitchFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    deletePitchFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            deletePitchFailure(e.getMessage());
        }
    }

    private void deletePitchSuccessfully(Boolean result){
        removePitchResult.setValue(result);
    }

    private void deletePitchFailure(String message){
        Log.v(TAG, message);
        removePitchResult.setValue(null);
    }

    public void verifyPitchRequest(Integer pitchId, MutableLiveData<Pitch> verifyPitchResult){
        this.verifyPitchResult=verifyPitchResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Pitch> verifyPitchCall = serverAPI.verifyPitchAsAdmin(pitchId);

            verifyPitchCall.enqueue(new Callback<Pitch>() {
                @Override
                public void onResponse(Call<Pitch> call, Response<Pitch> response) {
                    if(response.isSuccessful()){
                        Pitch result = new Pitch();
                        try {
                            result = (Pitch) response.body();
                        }catch (Exception e){
                            verifyPitchFailure(e.getMessage());
                        }
                        verifyPitchSuccessfully(result);
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        verifyPitchFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Pitch> call, Throwable t) {
                    verifyPitchFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            verifyPitchFailure(e.getMessage());
        }
    }

    private void verifyPitchSuccessfully(Pitch result){
        verifyPitchResult.setValue(result);
    }

    private void verifyPitchFailure(String message){
        Log.v(TAG, message);
        verifyPitchResult.setValue(null);
    }

}
