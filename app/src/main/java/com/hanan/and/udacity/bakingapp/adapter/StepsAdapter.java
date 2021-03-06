package com.hanan.and.udacity.bakingapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;
import com.hanan.and.udacity.bakingapp.ui.MasterRecipeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nono on 3/19/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepHolder> {
    private Context mContext;
    private List<Step> mSteps;
    private MasterRecipeFragment.OnStepClickListener mCallback;

    public StepsAdapter(Context mContext, List<Step> mSteps){
        this.mContext = mContext;
        this.mSteps = mSteps;
    }

    public StepsAdapter(Context mContext, List<Step> mSteps, MasterRecipeFragment.OnStepClickListener mCallback){
        this.mContext = mContext;
        this.mSteps = mSteps;
        this.mCallback = mCallback;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_layout, parent, false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.titleTextView.setText(position + ". " + step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int position;
        TextView titleTextView;
        public StepHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.step_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            position = getAdapterPosition();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Recipe.RECIPE_STEPS, (ArrayList) mSteps);
            bundle.putInt(Recipe.RECIPE_STEP_POSITION, position);
            mCallback.onStepSelected(bundle);
        }
    }
}
