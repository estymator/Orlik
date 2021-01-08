package com.example.orlik.ui.pitch;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.Network.AdminRequest;
import com.example.orlik.Network.PitchRequests;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.PitchConfirmations;
import com.example.orlik.data.model.Session;

public class PitchViewModel extends ViewModel {
    private final static String TAG="PitchViewModelTAG";
    private Pitch pitch;
    private PitchRequests pitchRequests = new PitchRequests();
    private MutableLiveData<PitchConfirmations> confirmPitchResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> deletePitchResult = new MutableLiveData<>();
    private MutableLiveData<Pitch> validPitchResult = new MutableLiveData<>();
    private boolean Admin;
    private AdminRequest adminRequest = new AdminRequest();
    Session session;


    public Pitch getPitch() {
        return pitch;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }

    public void setSession(Context context){
        session = new Session(context);
        if(session.getRole().equals("ROLE_ADMIN")){
            this.setAdmin(true);
        }
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public MutableLiveData<PitchConfirmations> getConfirmPitchResult() {
        return confirmPitchResult;
    }

    public void confirmPitch(Context context){
        String login = session.getLogin();
        pitchRequests.confirmPitch(pitch.getPitchId(), login, confirmPitchResult);
    }

    public void deletePitch(){
        adminRequest.deletePitchRequest(pitch.getPitchId(), deletePitchResult);
    }


    public void confirmPitchAsAdmin(){
        adminRequest.verifyPitchRequest(pitch.getPitchId(),validPitchResult);
    }

    public MutableLiveData<Boolean> getDeletePitchResult() {
        return deletePitchResult;
    }

    public MutableLiveData<Pitch> getValidPitchResult() {
        return validPitchResult;
    }
}
