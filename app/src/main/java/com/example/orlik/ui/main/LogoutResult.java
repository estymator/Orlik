package com.example.orlik.ui.main;

import androidx.annotation.Nullable;

import com.example.orlik.data.model.User;

public class LogoutResult {
    @Nullable
    private String result;


    LogoutResult(@Nullable String result) {
        this.result = result;
    }



    @Nullable
    String getResult() {
        return result;
    }


}
