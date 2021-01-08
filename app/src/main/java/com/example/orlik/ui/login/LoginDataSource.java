package com.example.orlik.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.Network.ServerAPI;

import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.UserDTO;

import java.io.Console;
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

            Call<UserDTO> loginCall= serverAPI.loginUser(username, password);
            loginCall.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

                   if(response.isSuccessful())
                   {

                       if(response.body().getLogin()!=null) {
                            String role=response.body().getRole();
                           Log.v(TAG, role);
                           User loggedInUser = new User(response.body().getTotalGames(),
                                   response.body().getWinGames(),
                                   response.body().getLogin(),
                                   response.body().getName(),
                                   response.body().getSurname(),
                                   response.body().getTrustRate(),
                                   response.body().isValid());
                            RetrofitServiceGenerator.setCredentials(username,password);
                           loginSuccessfull(loggedInUser, password, role);
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
                public void onFailure(Call<UserDTO> call, Throwable t) {

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

    private void loginSuccessfull(User loggedInUser, String password, String role)
    {
        if(this.loginResultMutableLiveData!=null)
        {
            this.loginResultMutableLiveData.setValue(new LoginResult(loggedInUser, password, role));
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