package com.example.myrecipes.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myrecipes.R;
import com.example.myrecipes.RecipesApi.ApiManager;
import com.example.myrecipes.models.Recipe;
import com.google.gson.Gson;
import com.example.myrecipes.models.SearchRecipes;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Serializable {
    public static final String RECIPES = "Recipes";
    private ApiManager recipeManager;
    private EditText ingredientInput;
    private ProgressBar progressBar;
    private ImageView searchImageView;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeManager = new ApiManager();
        ingredientInput = findViewById(R.id.ingredient_input);
        searchImageView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchRecipe();
            }
        });
    }

    private void SearchRecipe() {

        String currentText = ingredientInput.getText().toString();
        if (!TextUtils.isEmpty(currentText)) {
            recipeManager.getRecipes(currentText).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //hideProgressBar();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        SearchRecipes recipes = gson.fromJson(json, SearchRecipes.class);
                        List<Recipe> recipes_list = recipes.getRecipe();
                        Intent resultSearch = new Intent(MainActivity.this, Results.class);
                        resultSearch.putExtra(RECIPES, (Serializable) recipes_list);
                        startActivity(resultSearch);

                    }

                }
            });
        }
    }


}
