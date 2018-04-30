package com.example.myrecipes.models.RecipiesApi;

import com.example.myrecipes.models.RecipiesApi.Recipe;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class SearchRecipes implements Serializable {
    @SerializedName("count")
    private  int count;

    @SerializedName("hits")
    private List<Recipe> matchingRecipes;



public Integer getCount(){
    return count;

}
public List<Recipe> getRecipe(){
    return matchingRecipes;
}




}
