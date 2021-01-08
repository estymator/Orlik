package com.example.orlik.Network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.dto.NotificationDTO;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRequests {
    public final static String TAG="NotifiactionRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<Notification> addNotificationResult;
    private MutableLiveData<Boolean> deleteNotificationResult;
    private MutableLiveData<ArrayList<NotificationDTO>> getNotificationResult;
    private MutableLiveData<Notification> findNotificationResult;

    public void addNotificationRequest(String sourceLogin, String destinationLogin, int type, MutableLiveData<Notification> addNotificationResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.addNotificationResult=addNotificationResult;
        try{
            Call<Notification> addNotificationCall = serverAPI.addNotification(sourceLogin,destinationLogin,type);
            addNotificationCall.enqueue(new Callback<Notification>(){
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            Notification result= new Notification();
                            try{
                                result =(Notification) response.body();
                            }catch (Throwable t){
                                setNotificationFailure(t.getMessage());
                            }
                            addNotificationSuccess(result);
                        }else
                        {
                            setNotificationFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        setNotificationFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    setNotificationFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            setNotificationFailure(e.getMessage());
        }

    }

    private void addNotificationSuccess(Notification result){
        addNotificationResult.setValue(result);
    }
    private void setNotificationFailure(String message){
        Log.v(TAG,message);
        addNotificationResult.setValue(null);
    }

    public void deleteNotificationRequest(Integer id, MutableLiveData<Boolean> deleteNotificationResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.deleteNotificationResult=deleteNotificationResult;
        try{
            Call<Boolean> deleteNotificationCall = serverAPI.deleteNotification(id);
            deleteNotificationCall.enqueue(new Callback<Boolean>(){
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            Boolean result= new Boolean(false);
                            try{
                                result =(Boolean) response.body();
                            }catch (Throwable t){
                                deleteNotificationFailure(t.getMessage());
                            }
                            deleteNotificationSuccess(result);
                        }else
                        {
                            deleteNotificationFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        deleteNotificationFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    deleteNotificationFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            deleteNotificationFailure(e.getMessage());
        }
    }

    private void deleteNotificationSuccess(Boolean result){
        deleteNotificationResult.setValue(result);
    }
    private void deleteNotificationFailure(String message){
        Log.v(TAG,message);
        deleteNotificationResult.setValue(new Boolean(false));
    }

    public void getNotificationRequest(String login, MutableLiveData<ArrayList<NotificationDTO>> getNotificationResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.getNotificationResult=getNotificationResult;
        try{
            Call<ArrayList<NotificationDTO>> getNotificationCall = serverAPI.getNotifications(login);
            getNotificationCall.enqueue(new Callback<ArrayList<NotificationDTO>>(){
                @Override
                public void onResponse(Call<ArrayList<NotificationDTO>> call, Response<ArrayList<NotificationDTO>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<NotificationDTO> result= new ArrayList<>();
                            try{
                                result =(ArrayList<NotificationDTO>) response.body();
                            }catch (Throwable t){
                                getNotificationFailure(t.getMessage());
                            }
                            getNotificationSuccess(result);
                        }else
                        {
                            getNotificationFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        getNotificationFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<NotificationDTO>> call, Throwable t) {
                    getNotificationFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            getNotificationFailure(e.getMessage());
        }
    }

    private void getNotificationSuccess(ArrayList<NotificationDTO> result){
        getNotificationResult.setValue(result);
    }
    private void getNotificationFailure(String message){
        Log.v(TAG,message);
        getNotificationResult.setValue(new ArrayList<NotificationDTO>());
    }



    public void findNotificationRequest(String sourceLogin, String destinationLogin, int type, MutableLiveData<Notification> findNotificationResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.findNotificationResult=findNotificationResult;
        try{
            Call<Notification> findNotificationCall = serverAPI.findNotification(sourceLogin,destinationLogin,type);
            findNotificationCall.enqueue(new Callback<Notification>(){
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            Notification result= new Notification();
                            try{
                                result =(Notification) response.body();
                            }catch (Throwable t){
                                findNotificationFailure(t.getMessage());
                            }
                            findNotificationSuccess(result);
                        }else
                        {
                            findNotificationFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        findNotificationFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    findNotificationFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            findNotificationFailure(e.getMessage());
        }

    }

    private void findNotificationSuccess(Notification result){
        findNotificationResult.setValue(result);
    }
    private void findNotificationFailure(String message){
        Log.v(TAG,message);
        findNotificationResult.setValue(null);
    }
}
