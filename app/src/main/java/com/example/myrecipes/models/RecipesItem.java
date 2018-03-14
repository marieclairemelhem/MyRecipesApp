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
    private List<Ingredients> ingredientslist;


    public String getName(){
        return label;
    }
    public String getImg(){
        return img;
    }
    public List<Ingredients> getIngredientslist(){
        return ingredientslist;

    }
    public String getUrl(){
        return url;
    }
}
