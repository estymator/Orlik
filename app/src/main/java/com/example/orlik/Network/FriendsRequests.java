package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

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
}
