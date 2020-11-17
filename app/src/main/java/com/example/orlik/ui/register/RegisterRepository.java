package com.example.orlik.ui.register;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.Network.NetworkError;
import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.Network.ServerAPI;
import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private final static String TAG="RegisterRepositoryrTAG";
    private ServerAPI serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
    private MutableLiveData<RegisterResult> result;

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
        try {


            Call<User> loginCall = serverAPI.registerUser(login, password, name, surname, 1);

            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful() && response.body().getLogin().equals(login)) {
                        registerSuccesfull();

                    } else {
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        registerFailure(message);
                    }


                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    registerFailure(t.getMessage());

                }

            });

        }catch (Exception e)
        {
            registerFailure(e.getMessage());
        }
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
