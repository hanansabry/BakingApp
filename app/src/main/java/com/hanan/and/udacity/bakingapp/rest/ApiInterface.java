package com.hanan.and.udacity.bakingapp.rest;

import com.hanan.and.udacity.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Nono on 3/17/2018.
 */

public interface ApiInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
