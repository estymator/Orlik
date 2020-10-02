package com.example.orlik.ui.register;

import androidx.annotation.Nullable;

public class RegisterResult {

    @Nullable
    private String error;

    @Nullable
    public String getError() {
        return error;
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }

    public RegisterResult()
    {
        error=null;
    }
    public RegisterResult(String err)
    {
        error=err;
    }



}
