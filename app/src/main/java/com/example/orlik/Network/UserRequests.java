package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {
    private final static String TAG="UserRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<User> loggedInUser = new MutableLiveData<>();
    private MutableLiveData<ArrayList<User>> findUserResults = new MutableLiveData<>();

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
                                     response.body().getTrustRate(),
                                     response.body().isValid());

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

    public  void findUsers(String query, final MutableLiveData<ArrayList<User>> findUserResults){
        serverAPI = RetrofitServiceGenerator.createService(ServerAPI.class);
        this.findUserResults=findUserResults;
        try{
            Call<ArrayList<User>> findUserCall = serverAPI.findUsers(query);

            findUserCall.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    if(response.isSuccessful()){
                        ArrayList<User> result = new ArrayList<>();
                        try {
                            result = (ArrayList<User>) response.body();
                        }catch (Exception e){
                            findUsersFailure(e.getMessage());
                        }
                        findUsersSuccesfull(result);
                    }else{
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        Log.v(TAG,message);
                        findUsersFailure(message);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Log.v(TAG, t.getMessage());
                    findUsersFailure(t.getMessage());
                }
            });

        }catch (Exception e)
        {
            findUsersFailure(e.getMessage());
        }
    }


    public void findUsersSuccesfull(ArrayList<User> userArrayList)
    {
        this.findUserResults.setValue(userArrayList);
    }

    public void findUsersFailure(String message){
       Log.v(TAG, message);
    }
}
