package com.example.myrecipes.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class SearchRecipes implements Serializable {
    @SerializedName("count")
    private  int count;

    @SerializedName("hits")
    private List<Recipe> recipe;



public Integer getcount(){
    return count;

}
public List<Recipe> getRecipe(){
    return recipe;
}




}
