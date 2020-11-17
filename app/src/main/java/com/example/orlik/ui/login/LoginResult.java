package com.example.orlik.ui.login;

import androidx.annotation.Nullable;

import com.example.orlik.data.model.User;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private User success;
    @Nullable
    private String password;
    @Nullable
    private String error;

    LoginResult(@Nullable String error) {
        this.error = error;
    }

    LoginResult(@Nullable User success, @Nullable String passwd) {
        this.success = success;
        this.password=passwd;
    }

    @Nullable
    User getSuccess() {
        return success;
    }

    @Nullable
    String getPassword() {
        return password;
    }

    @Nullable
    String getError() {
        return error;
    }
}