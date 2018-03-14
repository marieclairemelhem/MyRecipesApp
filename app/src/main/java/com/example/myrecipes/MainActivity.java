package com.example.myrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.myrecipes.api.ApiManager;
import com.example.myrecipes.models.Recipe;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.example.myrecipes.R;


import com.example.myrecipes.models.SearchRecipes;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Serializable {
    private ApiManager recipesmanager;
    private EditText ingredient_search;
    private ProgressBar progressBar;
    private ImageView searchImageView;
    private Gson gson = new Gson();
    public static final String RECIPES = "Recipes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesmanager = new ApiManager();
        ingredient_search = findViewById(R.id.ingredient_input);
        searchImageView =(ImageView) findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchRecipe();
            }
        });
    }

    private void  SearchRecipe() {

        String currentText = ingredient_search.getText().toString();
        if (!TextUtils.isEmpty(currentText)) {
            recipesmanager.getRecipes(currentText).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //hideProgressBar();
                }

                @Override
                public void onResponse(Call call, Response response)throws IOException
                {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        SearchRecipes recipes = gson.fromJson(json, SearchRecipes.class);
                        List<Recipe> recipes_list = recipes.getRecipe();
                        Intent intent = new Intent(MainActivity.this, Results.class);
                        intent.putExtra(RECIPES,(Serializable) recipes_list);
                        startActivity(intent);

                    }

                }
            });
        }
    }




}
