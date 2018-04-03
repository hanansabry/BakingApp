package com.hanan.and.udacity.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Ingredient;

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

    Context mContext;
    ArrayList<Ingredient> mIngredients;

    public IngredientsListRemoteViewsFactory(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    //here I will get the data from shared preferences
    @Override
    public void onDataSetChanged() {
        //create test ingredients list
        mIngredients = new ArrayList<>();
        Ingredient i1 = new Ingredient();
        i1.setIngredient("butter");
        i1.setMeasure("cup");
        i1.setQuantity("3");
        mIngredients.add(i1);

        Ingredient i2 = new Ingredient();
        i2.setIngredient("sugar");
        i2.setMeasure("Spoon");
        i2.setQuantity("2");
        mIngredients.add(i2);

        Ingredient i3 = new Ingredient();
        i3.setIngredient("chocalate");
        i3.setMeasure("cup");
        i3.setQuantity("1");
        mIngredients.add(i3);
//        mIngredients = new ArrayList<>();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients.size();
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
        Intent fillInIntent = new Intent();
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
}
