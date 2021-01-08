package com.example.orlik.Network;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;

import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.PitchConfirmations;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PitchRequests {
    public final static String TAG = "PitchRequestsTAG";
    private ServerAPI serverAPI;
    private MutableLiveData<Pitch> addPitchResult;
    private MutableLiveData<ArrayList<Pitch>> pitchList;
    private MutableLiveData<PitchConfirmations> confirmPitchResult;
    private MutableLiveData<ArrayList<Pitch>> findPitchResult;
    public void getPitchInGivenRange(double lat, double lon, int range, boolean valid, String login, MutableLiveData<ArrayList<Pitch>> pitchList){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.pitchList=pitchList;
        try{
            Call<ArrayList<Pitch>> getPitchInGivenRangeCall = serverAPI.getPitch(lat,lon,range, valid, login);
            getPitchInGivenRangeCall.enqueue(new Callback<ArrayList<Pitch>>(){
                @Override
                public void onResponse(Call<ArrayList<Pitch>> call, Response<ArrayList<Pitch>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<Pitch> result= new ArrayList();
                            try{
                                result =(ArrayList<Pitch>) response.body();
                            }catch (Throwable t){
                                getPitchFailure(t.getMessage());
                            }
                            getPitchSuccesfully(result);
                        }else
                        {
                            getPitchFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        getPitchFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Pitch>> call, Throwable t) {
                    getPitchFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }
    }
    private void getPitchSuccesfully(ArrayList<Pitch> result){
        this.pitchList.setValue(result);
    }

    private void getPitchFailure(String mess){
        Log.v(TAG,mess);
    }

    public void addPitch(String localization, String address, String pitchType, MutableLiveData<Pitch> addPitchResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.addPitchResult=addPitchResult;
        try{
            Call<Pitch> addPitchCall = serverAPI.addPitch(pitchType,localization, address);
            addPitchCall.enqueue(new Callback<Pitch>(){
                @Override
                public void onResponse(Call<Pitch> call, Response<Pitch> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            Pitch result= new Pitch();
                            try{
                                result =(Pitch) response.body();
                            }catch (Throwable t){
                                addPitchFailure(t.getMessage());
                            }
                            addPitchSuccesfully(result);
                        }else
                        {
                            addPitchFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        addPitchFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<Pitch> call, Throwable t) {
                    addPitchFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            Log.v(TAG,e.getMessage());
        }

    }
    private void addPitchSuccesfully(Pitch result){
        this.addPitchResult.setValue(result);
    }

    private void addPitchFailure(String mess){
        Log.v(TAG,mess);
    }

    public void confirmPitch(int pitchId, String login, MutableLiveData<PitchConfirmations> confirmPitchResult){
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        this.confirmPitchResult=confirmPitchResult;
        try{
            Call<PitchConfirmations> confirmPitchCall = serverAPI.confirmPitch(pitchId, login);
            confirmPitchCall.enqueue(new Callback<PitchConfirmations>(){
                @Override
                public void onResponse(Call<PitchConfirmations> call, Response<PitchConfirmations> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            PitchConfirmations result= new PitchConfirmations();
                            try{
                                result =(PitchConfirmations) response.body();
                            }catch (Throwable t){
                                confirmPitchFailure(t.getMessage());
                            }
                            confirmPitchSuccesfully(result);
                        }else
                        {
                            confirmPitchFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        confirmPitchFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<PitchConfirmations> call, Throwable t) {
                    confirmPitchFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            confirmPitchFailure(e.getMessage());
        }

    }
    private void confirmPitchSuccesfully(PitchConfirmations result){
        this.confirmPitchResult.setValue(result);
    }

    private void confirmPitchFailure(String mess){
        Log.v(TAG,mess);
        this.confirmPitchResult.setValue(null);
    }

    public void findPitchRequest(String query, MutableLiveData<ArrayList<Pitch>> findPitchResult){
        this.findPitchResult=findPitchResult;
        this.serverAPI=RetrofitServiceGenerator.createService(ServerAPI.class);
        try{
            Call<ArrayList<Pitch>> findPitchCall = serverAPI.findPitch(query);
            findPitchCall.enqueue(new Callback<ArrayList<Pitch>>(){
                @Override
                public void onResponse(Call<ArrayList<Pitch>> call, Response<ArrayList<Pitch>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null) {
                            ArrayList<Pitch> result= new ArrayList<>();
                            try{
                                result =(ArrayList<Pitch>) response.body();
                            }catch (Throwable t){
                                findPitchFailure(t.getMessage());
                            }
                            findPitchSuccesfully(result);
                        }else
                        {
                            findPitchFailure("Bład pobrania danych");
                        }
                    }else{
                        Log.v(TAG,"ResponseSuccessful not 200");
                        Gson gson = new Gson();
                        NetworkError errorBody = gson.fromJson(response.errorBody().charStream(), NetworkError.class);
                        String message = errorBody.getMessage();
                        findPitchFailure(message);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Pitch>> call, Throwable t) {
                    findPitchFailure(t.getMessage());
                }
            });
        }catch (Exception e)
        {
            findPitchFailure(e.getMessage());
        }
    }

    private void findPitchSuccesfully(ArrayList<Pitch> result){
        this.findPitchResult.setValue(result);
    }

    private void findPitchFailure(String mess){
        Log.v(TAG,mess);
        this.findPitchResult.setValue(null);
    }

}
