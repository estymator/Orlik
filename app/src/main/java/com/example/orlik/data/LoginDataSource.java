package com.example.orlik.data;

import android.util.Log;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.Network.ServerAPI;
import com.example.orlik.data.model.LoggedInUser;
import com.example.orlik.data.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private final static String TAG="LoginDataSourceTAG";
    private ServerAPI serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
    public Result<LoggedInUser> login(String username, String password) {

        try {
            Call<User> loginCall= serverAPI.loginUser(username, password);

            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                   if(response.isSuccessful())
                   {
                       LoggedInUser loginuser= new LoggedInUser( java.util.UUID.randomUUID().toString(),
                               "Jane Doe");

                   }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.v(TAG,"Failure");
                }
            });
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}