package com.example.yasmeen.bakingapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface{
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
