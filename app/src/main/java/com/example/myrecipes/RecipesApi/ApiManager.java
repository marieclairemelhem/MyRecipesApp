package com.example.myrecipes.RecipesApi;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class ApiManager {

    private OkHttpClient client;

    public ApiManager() {
        client = new OkHttpClient();
    }

    public okhttp3.Call getRecipes(String food) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.edamam.com")
                .addPathSegment("search")
                .addQueryParameter("q", food)
                .addQueryParameter("app_id", "125afcf6")
                .addQueryParameter("app_key", "ec504b589a117a9e8112d6ce6bf15a65")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }


}