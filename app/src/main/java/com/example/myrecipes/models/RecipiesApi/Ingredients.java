package com.example.myrecipes.models.RecipiesApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Ingredients implements Serializable{

    @SerializedName("text")
    private String ingredientName;

    @SerializedName("weight")
    private double ingredientweight;

     public String getIngredientname()
     {
         return ingredientName;
     }
     public Double getIngredientweight(){
         return ingredientweight;
     }
}
