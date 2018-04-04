package com.hanan.and.udacity.bakingapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;
import java.util.ArrayList;
import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private int stepPosition;
    private List<Step> steps;
    private Button next, previous;
    private TextView stepsProgress;
    private String recipeName;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        fragmentManager = getSupportFragmentManager();
        Bundle bundle;
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        } else {
            bundle = getIntent().getExtras();
        }

        recipeName = bundle.getString(Recipe.RECIPE_NAME);
        steps = bundle.getParcelableArrayList(Recipe.RECIPE_STEPS);
        stepPosition = bundle.getInt(Recipe.RECIPE_STEP_POSITION);
        bundle.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.stepper_layout).setVisibility(View.GONE);
        } else {
            next = findViewById(R.id.next);
            previous = findViewById(R.id.previous);
            stepsProgress = findViewById(R.id.steps_progress);
            Toolbar toolbar = findViewById(R.id.my_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            setTitle(recipeName);
            stepsProgress.setText(setStepProgressText());

            if (stepPosition == 0) {
                previous.setClickable(false);
                previous.setText("");
            } else if (stepPosition == steps.size() - 1) {
                next.setClickable(false);
                next.setText(getResources().getString(R.string.complete));
            }
        }

        StepFragment stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.step_container);
        if (stepFragment == null) {
            stepFragment = new StepFragment();
        }
        stepFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();
    }

    private String setStepProgressText() {
        return String.format(getResources().getString(R.string.step_progress), stepPosition, (steps.size() - 1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addStepFragment(Bundle bundle) {
        StepFragment stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.step_container);
        if (stepFragment == null) {
            stepFragment = new StepFragment();
        }
        stepFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();
    }


    public void onNextClicked(View view) {
        stepPosition++;
        Step nextStep = steps.get(stepPosition);
        Bundle b = new Bundle();
        b.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));

//        addStepFragment(b);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(b);
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();

        if (stepPosition == 1) {
            previous.setClickable(true);
            previous.setText(R.string.previous);
        } else if (stepPosition == steps.size() - 1) {
            next.setClickable(false);
            next.setText(getResources().getString(R.string.complete));
        }
        stepsProgress.setText(setStepProgressText());
    }

    public void onPreviousClicked(View view) {
        stepPosition--;
        Step nextStep = steps.get(stepPosition);

        Bundle b = new Bundle();
        b.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));
//        addStepFragment(b);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(b);
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();

        if (stepPosition == 0) {
            previous.setClickable(false);
            previous.setText("");
        } else if (stepPosition == steps.size() - 2) {
            next.setClickable(true);
            next.setText(R.string.next);
        }

        stepsProgress.setText(setStepProgressText());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Recipe.RECIPE_STEPS, (ArrayList) steps);
        outState.putInt(Recipe.RECIPE_STEP_POSITION, stepPosition);
        outState.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));
        outState.putString(Recipe.RECIPE_NAME, recipeName);
    }
}