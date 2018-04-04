package com.hanan.and.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.adapter.IngredientsAdapter;
import com.hanan.and.udacity.bakingapp.adapter.RecipesAdapter;
import com.hanan.and.udacity.bakingapp.adapter.StepsAdapter;
import com.hanan.and.udacity.bakingapp.model.Ingredient;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements MasterRecipeFragment.OnStepClickListener {
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private String recipeName;
    private int recipeThumb;
    Recipe recipe;
    public static boolean twoPane;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = findViewById(R.id.recipe_activity_toolbar);
        setSupportActionBar(toolbar);
        //add up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipe = bundle.getParcelable(Recipe.RECIPE);
        recipeThumb = recipe.getImage();
        recipeName = recipe.getName();

        setTitle(recipeName);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MasterRecipeFragment recipeFragment = new MasterRecipeFragment();
        recipeFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.master_recipe_fragment, recipeFragment)
                .commit();

        if (findViewById(R.id.two_pane_layout) != null) {
            twoPane = true;
            setTitle(recipeName);

            StepFragment stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.step_container);
            if (stepFragment == null) {
                stepFragment = new StepFragment();
            }
            Bundle b = new Bundle();
            if (savedInstanceState == null) {
                b.putParcelable(Recipe.RECIPE_STEP, recipe.getSteps().get(0));
            } else {
                position = savedInstanceState.getInt(Recipe.RECIPE_STEP_POSITION);
                b.putParcelable(Recipe.RECIPE_STEP, recipe.getSteps().get(position));
            }
            stepFragment.setArguments(b);
            // Add the fragment to its container using a transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
        } else {
            twoPane = false;
            initCollapsingToolbar();

            try {
                Glide.with(this)
                        .load(recipeThumb)
                        .into((ImageView) findViewById(R.id.recipe_backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepSelected(Bundle bundle) {
        position = bundle.getInt(Recipe.RECIPE_STEP_POSITION);
        if (twoPane) {
            StepFragment stepFragment = new StepFragment();
            bundle.putParcelable(Recipe.RECIPE_STEP, recipe.getSteps().get(position));
            stepFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            bundle.putString(Recipe.RECIPE_NAME, recipeName);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Recipe.RECIPE_STEP_POSITION, position);
    }
}
