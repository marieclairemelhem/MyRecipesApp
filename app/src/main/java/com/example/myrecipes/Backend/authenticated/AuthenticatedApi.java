package com.example.myrecipes.Backend.authenticated;

import com.example.myrecipes.models.RecipiesApi.RecipesItem;
import com.example.myrecipes.models.Users.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface AuthenticatedApi {

    @POST("recipes")
    Call <List<RecipesItem>> addRecipes(@Body RecipesItem recipe);


    @GET("profile")
    Call<User> getProfile();



    @GET("recipes")
    Call<List<RecipesItem>> getRecipesSaved();


    @DELETE("recipes/{recipesId}")
    Call<List<RecipesItem>> deleteRecipe( @Path("recipesId") String id);


}
