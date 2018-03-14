package com.example.myrecipes.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Ingredients implements Serializable{

    @SerializedName("text")
    private String ingredientname;

    @SerializedName("weight")
    private double ingredientweight;

     public String getIngredientname()
     {
         return ingredientname;
     }
     public Double getIngredientweight(){
         return ingredientweight;
     }
}
