package com.hanan.and.udacity.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.ui.MasterRecipeFragment;
import com.hanan.and.udacity.bakingapp.ui.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nono on 3/17/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeHolder> {
    private Context mContext;
    private List<Recipe> recipeList;

    public RecipesAdapter(Context mContext, List<Recipe> recipeList) {
        this.mContext = mContext;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.title.setText(recipe.getName());

        //loading recipe image using glide library
        Glide.with(mContext)
                .load(recipe.getImage())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView thumbnail;
        int position;

        public RecipeHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            position = getAdapterPosition();
            Recipe recipe = recipeList.get(position);

            //save the selected recipe and it's ingredients in shared preferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(recipe.getIngredients());
            editor.putString(Recipe.RECIPE_INGREDIENTS_STRING, json);
            editor.putString(Recipe.RECIPE, gson.toJson(recipe));
            editor.putString(Recipe.RECIPE_NAME, recipe.getName());
            editor.commit();
            //update the widget
            Intent widgetIntent = IngredientsListRemoteViewsFactory.updateWidgetList(mContext);
            mContext.sendBroadcast(widgetIntent);

            //start the recipe activity
            Bundle bundle = new Bundle();
            bundle.putParcelable(Recipe.RECIPE, recipe);
            Intent intent = new Intent(mContext, RecipeActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }
}
