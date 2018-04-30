package com.example.myrecipes.Backend.authentication;

import com.example.myrecipes.models.Users.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface AuthenticationApi {

    @POST("register")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);



}
