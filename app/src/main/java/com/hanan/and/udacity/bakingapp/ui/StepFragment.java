package com.hanan.and.udacity.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hanan.and.udacity.bakingapp.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * Created by Nono on 3/21/2018.
 */

public class StepFragment extends Fragment implements Step {
    TextView stepDescTextView, stepNameTextView;
    String stepDescription, stepName;
    com.hanan.and.udacity.bakingapp.model.Step recipeStep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        stepDescTextView = rootView.findViewById(R.id.step_description);
        stepNameTextView = rootView.findViewById(R.id.step_name);

        Bundle b = getArguments();
        recipeStep = b.getParcelable("STEP");
        stepName = recipeStep.getShortDescription();
        stepDescription = recipeStep.getDescription();

        if (RecipeActivity.twoPane) {
            stepNameTextView.setText(stepName);
            stepDescTextView.setText(stepDescription);
        }
        return rootView;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        stepNameTextView.setText(stepName);
        stepDescTextView.setText(stepDescription);
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
    }
}
