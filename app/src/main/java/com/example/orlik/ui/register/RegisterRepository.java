package com.example.orlik.ui.register;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.MainActivity;
import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.Network.ServerAPI;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private final static String TAG="RegisterRepositoryrTAG";
    private ServerAPI serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
    MutableLiveData<RegisterResult> result;

    /**
     * Make asynchronous register call and save result to mutableLiveData registerResult object
     * @param login
     * @param password
     * @param name
     * @param surname
     * @param result - mutableLiveData registerResult object
     */
    public void register(final String login, String password, String name, String surname , MutableLiveData<RegisterResult> result)
    {

        this.result=result;
        Call<User> loginCall= serverAPI.registerUser(login,password, name, 1);

        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               if(response.isSuccessful() && response.body().getLogin().equals(login))
               {
                   Log.v(TAG,"Zarejestrowano");
                   registerSuccesfull();



               }else {
                   Log.v(TAG,"Bląd"+response.code()+response.message());
                   String message= "code:"+response.code()+" "+response.message();
                   registerFailure(message);
               }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                Log.v(TAG,"Failure");
                registerFailure(t.getMessage());

            }

        });
        Log.v(TAG,"Zwracam wartosc rejestracj");

    }

    public void registerSuccesfull()
    {
        if(this.result!=null)
        {
            this.result.setValue(new RegisterResult());
        }
    }

    public void registerFailure(String message)
    {
        if(this.result!=null)
        {
            this.result.setValue(new RegisterResult(message));
        }
    }
}
