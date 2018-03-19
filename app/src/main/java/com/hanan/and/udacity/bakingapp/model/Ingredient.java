package com.hanan.and.udacity.bakingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nono on 3/17/2018.
 */

public class Ingredient {
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
