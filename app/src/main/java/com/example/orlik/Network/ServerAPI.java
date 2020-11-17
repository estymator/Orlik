package com.example.orlik.Network;

import com.example.orlik.data.model.User;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerAPI {

    @POST("/login")
    Call<User> loginUser(
            @Query("login") String login,
            @Query("password") String password
    );


    @POST("/register")
    Call<User> registerUser(
            @Query("login") String login,
            @Query("password") String password,
            @Query("name") String name,
            @Query("surname") String surname,
            @Query("userRole") int userRole
    );

    @GET("/logout")
    Call<Object> logoutUser();

    @GET("admin/user/all")
    Call<List<User>> getAllUsers();

    @GET("/home")
    Call<User> getLoggedInUser();
}
