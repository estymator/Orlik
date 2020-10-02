package com.example.orlik.Network;

import com.example.orlik.data.model.User;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerAPI {

    @Headers("Cache-Control: max-age=640000")
    @POST("/login")
    Call<User> loginUser(
            @Query("login") String login,
            @Query("password") String password
    );


    @POST("/register")
    Call<User> registerUser(
            @Query("login") String login,
            @Query("password") String password,
            @Query("email") String email,
            @Query("userRole") int userRole
    );
}
