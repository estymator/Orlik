package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsRequests {
    private final static String TAG="FriendsRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<ArrayList<User>> friendsList;
    private MutableLiveData<Boolean> deleteFriendsResult;
    private MutableLiveData<Friends> addFriendsResult;


    public void loadFriends(String login, MutableLiveData<ArrayList<User>> friendsList){
        this.friendsList=friendsList;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<User>> getFriendsCall = serverAPI.getFriends(login);

            getFriendsCall.enqueue(new Callback<ArrayList<User>>(){
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            ArrayList<User> result= new ArrayList();
                            try{
                                result = (ArrayList<User>) response.body();
                            }catch (Throwable t){
                                getAllFriendsFailure(t.getMessage());
                            }
                            getAllFriendsSuccess(result);
                        }else
                        {
                            getAllFriendsFailure("Bład pobrania danych");
                        }
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        getAllFriendsFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    getAllFriendsFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }

    }


    private void getAllFriendsSuccess(ArrayList<User> result){
        this.friendsList.setValue(result);
    }
    private void getAllFriendsFailure(String mess){
        Log.v(TAG, mess);
        this.friendsList.setValue(new ArrayList<User>());
    }



    public void deleteFriends(String firstLogin, String secondLogin, MutableLiveData<Boolean> deleteFriendsResult){
        this.deleteFriendsResult=deleteFriendsResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Boolean> deleteFriendsCall = serverAPI.deleteFriends(firstLogin,secondLogin);

            deleteFriendsCall.enqueue(new Callback<Boolean>(){
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            Boolean result = new Boolean(false);
                            try{
                                result = (Boolean) response.body();
                            }catch (Throwable t){
                                getAllFriendsFailure(t.getMessage());
                            }
                            deleteFriendsSuccess(result);
                        }else
                        {
                            deleteFriendsFailure("Bład pobrania danych");
                        }
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        deleteFriendsFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    deleteFriendsFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
            deleteFriendsFailure(e.getMessage());
        }

    }


    private void deleteFriendsSuccess(Boolean result){
        this.deleteFriendsResult.setValue(result);
    }
    private void deleteFriendsFailure(String mess){
        Log.v(TAG, mess);
        this.deleteFriendsResult.setValue(false);
    }

    public void addFriends(String firstLogin, String secondLogin, MutableLiveData<Friends> addFriendsResult){
        this.addFriendsResult=addFriendsResult;
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<Friends> addFriendsCall = serverAPI.addFriends(firstLogin,secondLogin);

            addFriendsCall.enqueue(new Callback<Friends>(){
                @Override
                public void onResponse(Call<Friends> call, Response<Friends> response) {
                    if(response.isSuccessful()){

                        if(response.body()!=null) {
                            Friends result = new Friends();
                            try{
                                result = (Friends) response.body();
                            }catch (Throwable t){
                                getAllFriendsFailure(t.getMessage());
                            }
                            addFriendsSuccess(result);
                        }else
                        {
                            addFriendsFailure("Bład pobrania danych");
                        }
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        addFriendsFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<Friends> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    addFriendsFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
            addFriendsFailure(e.getMessage());
        }

    }


    private void addFriendsSuccess(Friends result){
        this.addFriendsResult.setValue(result);
    }
    private void addFriendsFailure(String mess){
        Log.v(TAG, mess);
        this.addFriendsResult.setValue(new Friends()); //TODO add fetchError attribute to Friends
    }

}
