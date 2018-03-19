package com.hanan.and.udacity.bakingapp.model;

import com.google.gson.annotations.SerializedName;
import com.hanan.and.udacity.bakingapp.R;

import java.util.List;

/**
 * Created by Nono on 3/17/2018.
 */

public class Recipe {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    private List<Step> steps;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;

    static int[] images = new int[]{
            R.drawable.nutella_pie,
            R.drawable.brownies,
            R.drawable.moist_yellow_cake,
            R.drawable.cheesecake
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getImage() {
        if (id >=1 && id <=4)
            return images[id-1];
        else
            return R.drawable.app_cover;

    }

    public void setImage(String image) {
        this.image = image;
    }
}
