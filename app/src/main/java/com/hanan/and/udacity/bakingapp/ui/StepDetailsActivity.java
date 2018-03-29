package com.hanan.and.udacity.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.adapter.StepsAdapter;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;
import com.stepstone.stepper.StepperLayout;

import java.util.ArrayList;
import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {

    private StepperLayout mStepperLayout;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private int stepPosition;
    private List<Step> steps;
    private Button next, previous;
    private TextView stepsProgress;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        stepsProgress = findViewById(R.id.steps_progress);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StepFragment stepFragment = new StepFragment();
        if (savedInstanceState != null) {
            recipeName = savedInstanceState.getString(Recipe.RECIPE_NAME);
            steps = savedInstanceState.getParcelableArrayList(Recipe.RECIPE_STEPS);
            stepPosition = savedInstanceState.getInt(Recipe.RECIPE_STEP_POSITION);
            addStepFragment(stepFragment, false, savedInstanceState);
        } else {
            Bundle bundle = getIntent().getExtras();
            recipeName = bundle.getString(Recipe.RECIPE_NAME);
            steps = bundle.getParcelableArrayList(Recipe.RECIPE_STEPS);
            stepPosition = bundle.getInt(Recipe.RECIPE_STEP_POSITION);
            bundle.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));
            addStepFragment(stepFragment, true, bundle);
        }

        setTitle(recipeName);
        stepsProgress.setText(stepPosition + " / " + (steps.size() - 1));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.stepper_layout).setVisibility(View.GONE);
        }

        if (stepPosition == 0) {
            previous.setClickable(false);
            previous.setText("");
        } else if (stepPosition == steps.size() - 1) {
            next.setClickable(false);
            next.setText("COMPLETE");
        }
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

    public void addStepFragment(StepFragment stepFragment, boolean add, Bundle bundle) {
        stepFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (add) {
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
        }
    }

    public void onNextClicked(View view) {
        stepPosition++;
        Step nextStep = steps.get(stepPosition);
        Bundle b = new Bundle();
        b.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));

        StepFragment fragment = new StepFragment();
        addStepFragment(fragment, false, b);

        if (stepPosition == 1) {
            previous.setClickable(true);
            previous.setText(R.string.previous);
        } else if (stepPosition == steps.size() - 1) {
            next.setClickable(false);
            next.setText("COMPLETE");
        }
        stepsProgress.setText(stepPosition + " / " + (steps.size() - 1));
    }

    public void onPreviousClicked(View view) {
        stepPosition--;
        Step nextStep = steps.get(stepPosition);
        Bundle b = new Bundle();
        b.putParcelable(Recipe.RECIPE_STEP, steps.get(stepPosition));
        StepFragment fragment = new StepFragment();
        addStepFragment(fragment, false, b);

        if(stepPosition == 0){
            previous.setClickable(false);
            previous.setText("");
        }else if(stepPosition == steps.size() - 2){
            next.setClickable(true);
            next.setText(R.string.next);
        }

        stepsProgress.setText(stepPosition + " / " + (steps.size() - 1));
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