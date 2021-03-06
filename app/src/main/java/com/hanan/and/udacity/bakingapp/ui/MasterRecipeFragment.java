package com.hanan.and.udacity.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.adapter.IngredientsAdapter;
import com.hanan.and.udacity.bakingapp.adapter.StepsAdapter;
import com.hanan.and.udacity.bakingapp.model.Ingredient;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by Nono on 3/19/2018.
 */

public class MasterRecipeFragment extends Fragment {

    private String recipeName;
    private int recipeThumb;
    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(Bundle bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    public MasterRecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_recipe, container, false);

        Bundle bundle = getArguments();
        Recipe recipe = bundle.getParcelable(Recipe.RECIPE);

        List<Ingredient> ingredients = recipe.getIngredients();
        List<Step> steps = recipe.getSteps();

        RecyclerView ingredientsRV = rootView.findViewById(R.id.ingredients_rv);
        RecyclerView.LayoutManager layout1 = new LinearLayoutManager(getContext());
        ingredientsRV.setLayoutManager(layout1);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredients);
        ingredientsRV.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        RecyclerView stepsRV = rootView.findViewById(R.id.steps_rv);
        RecyclerView.LayoutManager layout2 = new LinearLayoutManager(getContext());
        stepsRV.setLayoutManager(layout2);
        stepsRV.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 0));
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), steps, mCallback);
        stepsRV.setAdapter(stepsAdapter);
        stepsAdapter.notifyDataSetChanged();

        return rootView;
    }
}
