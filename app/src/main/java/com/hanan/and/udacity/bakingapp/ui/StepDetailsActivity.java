package com.hanan.and.udacity.bakingapp.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.adapter.StepperAdapter;
import com.hanan.and.udacity.bakingapp.adapter.StepsAdapter;
import com.hanan.and.udacity.bakingapp.model.Step;
import com.stepstone.stepper.StepperLayout;

import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Step Details");

        Bundle bundle = getIntent().getExtras();
        List<Step> steps = bundle.getParcelableArrayList(StepsAdapter.RECIPE_STEPS);
        int stepPosition = bundle.getInt(StepsAdapter.STEP_POSITION);

        mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this, steps));
        mStepperLayout.setCurrentStepPosition(stepPosition);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
