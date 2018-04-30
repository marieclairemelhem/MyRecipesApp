package com.example.myrecipes.Backend.authenticated;

import android.content.Context;


import com.example.myrecipes.localstorage.LocalStorageManager;
import com.example.myrecipes.models.RecipiesApi.RecipesItem;
import com.example.myrecipes.models.Users.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AuthenticatedApiManager {

    private LocalStorageManager localStorageManager;
    private OkHttpClient client;
    private Retrofit retrofit;
    public static final String BASE_URL = "http://10.0.2.2:3000/";
    private AuthenticatedApi authenticationApi;
    private String token;
    private static AuthenticatedApiManager authenticationApiManager;

    private AuthenticatedApiManager(Context context) {

        localStorageManager = LocalStorageManager.getInstance(context);
        User user = localStorageManager.getUser();
        token = user.getToken();

        client = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new AuthInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authenticationApi = retrofit.create(AuthenticatedApi.class);
    }



    public static AuthenticatedApiManager getInstance(Context context) {
        if (authenticationApiManager == null) {
            authenticationApiManager = new AuthenticatedApiManager(context);
        }
        return authenticationApiManager;
    }

    public Call<List<RecipesItem>> addRecipes(RecipesItem recipe) {
        return authenticationApi.addRecipes(recipe);
    }
    public Call<User> getProfile() {
        return authenticationApi.getProfile();

    }
    public Call<List<RecipesItem>> getRecipesSaved() {
        return authenticationApi.getRecipesSaved();
    }

    public Call<List<RecipesItem>> deleteRecipe(String recipeid) {
        return authenticationApi.deleteRecipe(recipeid);
    }

    private class AuthInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request authorization = chain.request().newBuilder().addHeader("Authorization", token).build();
            return chain.proceed(authorization);


        }
    }
}
