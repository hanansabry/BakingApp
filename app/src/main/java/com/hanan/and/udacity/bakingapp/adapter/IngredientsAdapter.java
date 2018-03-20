package com.hanan.and.udacity.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by Nono on 3/19/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder>{
    Context mContext;
    List<Ingredient> mIngredients;

    public IngredientsAdapter(Context mContext, List<Ingredient> mIngredients){
        this.mContext = mContext;
        this.mIngredients = mIngredients;
    }

    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_layout, parent, false);
        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        holder.nameTextView.setText(ingredient.getIngredient());
        holder.quantityTextView.setText(ingredient.getQuantity());
        holder.measureTextView.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, quantityTextView, measureTextView;
        int position;

        public IngredientHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.ingredient_name);
            quantityTextView = itemView.findViewById(R.id.ingredient_quantity);
            measureTextView = itemView.findViewById(R.id.ingredient_measure);
        }
    }
}
