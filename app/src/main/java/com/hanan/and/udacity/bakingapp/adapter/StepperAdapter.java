package com.hanan.and.udacity.bakingapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.ui.StepFragment;
import com.hanan.and.udacity.bakingapp.model.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.List;

/**
 * Created by Nono on 3/21/2018.
 */

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private List<Step> mStepsList;
    public StepperAdapter(@NonNull FragmentManager fm
            , @NonNull Context context
            , List<Step> mStepsList) {
        super(fm, context);
        this.mStepsList = mStepsList;
    }


    @Override
    public com.stepstone.stepper.Step createStep(int position) {
        StepFragment step = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("STEP_POSITION", mStepsList.get(position));
        step.setArguments(bundle);
        return step;
    }

    @Override
    public int getCount() {
        return mStepsList.size();
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context)
                .setTitle("test tab title") //can be a CharSequence instead
                .create();
    }
}
