package com.example.orlik.ui.register;

import android.util.Patterns;

import androidx.annotation.Nullable;

public class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer secPasswordError;
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer surnameError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,@Nullable Integer secPassword, @Nullable Integer nameError, @Nullable Integer surnameError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.secPasswordError=secPassword;
        this.nameError = nameError;
        this.surnameError = surnameError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.nameError=null;
        this.surnameError=null;
        this.isDataValid = isDataValid;
    }

    boolean isDataValid() {
        return isDataValid;
    }



    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    public Integer getSecPasswordError() {
        return secPasswordError;
    }


    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    @Nullable
    public Integer getSurnameError() {
        return surnameError;
    }


}
