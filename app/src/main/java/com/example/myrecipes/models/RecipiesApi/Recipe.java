package com.example.myrecipes.models.RecipiesApi;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recipe implements Serializable{
 @SerializedName("recipe")
    private RecipesItem recipeObject;

 public RecipesItem getRecipeobj (){
     return recipeObject;
 }
}
