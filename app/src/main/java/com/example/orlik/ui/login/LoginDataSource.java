package com.example.orlik.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.Network.ServerAPI;

import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;

import java.io.IOException;
import java.net.UnknownServiceException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private final static String TAG="LoginDataSourceTAG";
    private MutableLiveData<LoginResult> loginResultMutableLiveData;
    private ServerAPI serverAPI;
    public void login(final String username, final String password, MutableLiveData<LoginResult> loginResultMutableLiveData) {
        this.serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class,username,password);
        this.loginResultMutableLiveData=loginResultMutableLiveData;

        try {

            Call<User> loginCall= serverAPI.loginUser(username, password);
            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                   if(response.isSuccessful())
                   {

                       if(response.body().getLogin()!=null) {
                           User loggedInUser = new User(response.body().getTotalGames(),
                                   response.body().getWinGames(),
                                   response.body().getLogin(),
                                   response.body().getName(),
                                   response.body().getSurname(),
                                   response.body().getTrustRate());
                            RetrofitServiceGenerator.setCredentials(username,password);
                           loginSuccessfull(loggedInUser, password);
                       }else
                       {
                           loginFailed("Blędne dane logowania");
                       }

                   }else
                   {
                       loginFailed("Logowanie nieudane");
                   }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    loginFailed(t.getMessage());
                    
                }
            });

        } catch (Exception e) {
            loginFailed(e.getMessage());
        }
    }


    public void logout() {
        // TODO: revoke authentication
    }

    private void loginSuccessfull(User loggedInUser, String password)
    {


        if(this.loginResultMutableLiveData!=null)
        {
            this.loginResultMutableLiveData.setValue(new LoginResult(loggedInUser, password));
        }
    }

    private void loginFailed(String message)
    {
        Log.v(TAG, message);
        if(this.loginResultMutableLiveData!=null)
        {
            this.loginResultMutableLiveData.setValue(new LoginResult(message));
        }
    }
}