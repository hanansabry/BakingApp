package com.hanan.and.udacity.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hanan.and.udacity.bakingapp.R;

import java.util.List;

/**
 * Created by Nono on 3/17/2018.
 */

public class Recipe implements Parcelable{
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

    public Recipe(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        ingredients = parcel.readArrayList(getClass().getClassLoader());
        steps = parcel.readArrayList(getClass().getClassLoader());
        servings = parcel.readInt();
        image = parcel.readString();
    }

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[0];
        }
    };
}
