package com.example.myrecipes.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RecipesItem implements Serializable{

    @SerializedName("label")
    private String label;

    @SerializedName("image")
    private String img;
    @SerializedName("url")
    private String url;

    @SerializedName("ingredients")
    private List<Ingredients> ingredientsList;


    public String getName(){
        return label;
    }
    public String getImg(){
        return img;
    }
    public List<Ingredients> getIngredientsList(){
        return ingredientsList;

    }
    public String getUrl(){
        return url;
    }
}
