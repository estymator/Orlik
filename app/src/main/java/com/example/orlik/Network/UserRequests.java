package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {
    private final static String TAG="UserRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<User> loggedInUser = new MutableLiveData<>();
     public  void getLoggedInUser(final MutableLiveData<User> loggedInUser){
         serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
         this.loggedInUser=loggedInUser;
         try{
             Call<User> loggedInUserCall = serverAPI.getLoggedInUser();

             loggedInUserCall.enqueue(new Callback<User>() {
                 @Override
                 public void onResponse(Call<User> call, Response<User> response) {
                     if(response.isSuccessful()){

                         if(response.body().getLogin()!=null) {
                             User loggedInUser = new User(response.body().getTotalGames(),
                                     response.body().getWinGames(),
                                     response.body().getLogin(),
                                     response.body().getName(),
                                     response.body().getSurname(),
                                     response.body().getTrustRate());

                             loggedInUserSuccesfull(loggedInUser);
                         }else
                         {
                             loggedInUserFailure("Brak autoryzacji");
                         }
                     }else{
                         Gson gson = new Gson();
                         NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                         String message = errorBody.getMessage();
                         Log.v(TAG,message);
                         loggedInUserFailure(message);
                     }
                 }

                 @Override
                 public void onFailure(Call<User> call, Throwable t) {
                     Log.v(TAG, t.getMessage());
                     loggedInUserFailure(t.getMessage());
                 }
             });

         }catch (Exception e)
         {
             Log.v(TAG,e.getMessage());
         }
     }


     public void loggedInUserSuccesfull(User user)
     {
        this.loggedInUser.setValue(user);
     }
     public void loggedInUserFailure(String message){
        this.loggedInUser.setValue(new User(message));
     }
}
