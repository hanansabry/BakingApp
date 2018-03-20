package com.hanan.and.udacity.bakingapp.ui;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.adapter.IngredientsAdapter;
import com.hanan.and.udacity.bakingapp.adapter.RecipesAdapter;
import com.hanan.and.udacity.bakingapp.adapter.StepsAdapter;
import com.hanan.and.udacity.bakingapp.model.Ingredient;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private String recipeName;
    private int recipeThumb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Recipe recipe = bundle.getParcelable(RecipesAdapter.RECIPE);
        recipeThumb = recipe.getImage();
        recipeName = recipe.getName();

        try {
            Glide.with(this)
                    .load(recipeThumb)
                    .into((ImageView) findViewById(R.id.recipe_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(recipeName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
