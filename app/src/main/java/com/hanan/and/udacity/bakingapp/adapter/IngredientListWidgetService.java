package com.hanan.and.udacity.bakingapp.adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Ingredient;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.ui.RecipeWidgetProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Nono on 4/3/2018.
 */

public class IngredientListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListRemoteViewsFactory(this.getApplicationContext());
    }
}

class IngredientsListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<Ingredient> mIngredients;
    private Recipe recipe;

    public IngredientsListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    //here I will get the data from shared preferences
    @Override
    public void onDataSetChanged() {
        //get the recipe ingredients from SharePreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String ingredientsJson = sharedPreferences.getString(Recipe.RECIPE_INGREDIENTS_STRING, null);
        Type typeIngredient = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        mIngredients = gson.fromJson(ingredientsJson, typeIngredient);

        String recipeJson = sharedPreferences.getString(Recipe.RECIPE, null);
        Type typeRecipe = new TypeToken<Recipe>() {}.getType();
        recipe = gson.fromJson(recipeJson, typeRecipe);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    //populate items with its data
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item_layout);

        Ingredient ingredient = mIngredients.get(position);
        views.setTextViewText(R.id.ingredient_name, ingredient.getIngredient());
        views.setTextViewText(R.id.ingredient_measure, ingredient.getMeasure());
        views.setTextViewText(R.id.ingredient_quantity, ingredient.getQuantity());

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
        Bundle bundle = new Bundle();
        bundle.putParcelable(Recipe.RECIPE, recipe);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(bundle);
        views.setOnClickFillInIntent(R.id.ingredient_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public static Intent updateWidgetList(Context context) {
        Intent intent = new Intent(context, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        return intent;
    }
}
