package com.hanan.and.udacity.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanan.and.udacity.bakingapp.R;
import com.hanan.and.udacity.bakingapp.model.Step;

import java.util.List;

/**
 * Created by Nono on 3/19/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepHolder> {
    Context mContext;
    List<Step> mSteps;

    public StepsAdapter(Context mContext, List<Step> mSteps){
        this.mContext = mContext;
        this.mSteps = mSteps;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_layout, parent, false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.titleTextView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        public StepHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.step_title);
        }
    }
}
