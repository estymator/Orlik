package com.example.orlik.data.model;

import androidx.lifecycle.MutableLiveData;

public class PitchConfirmations {
    private Integer pitchId;
    private String userLogin;
    public Integer getPitchId() {
        return pitchId;
    }

    public void setPitchId(Integer pitchId) {
        this.pitchId = pitchId;
    }

    public PitchConfirmations() {
    }

    public PitchConfirmations(Integer pitchId, String userLogin) {
        this.pitchId = pitchId;
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
