package com.hanan.and.udacity.bakingapp.ui;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.HashMap;

/**
 * Created by Nono on 3/21/2018.
 */

public class StepFragment extends Fragment implements Step {
    private TextView stepDescTextView, stepNameTextView;
    private String stepDescription, stepName;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private com.hanan.and.udacity.bakingapp.model.Step recipeStep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        stepDescTextView = rootView.findViewById(R.id.step_description);
        stepNameTextView = rootView.findViewById(R.id.step_name);
        mPlayerView = rootView.findViewById(R.id.player_view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        Bundle b = getArguments();
        recipeStep = b.getParcelable("STEP");
        stepName = recipeStep.getShortDescription();
        stepDescription = recipeStep.getDescription();

        if (RecipeActivity.twoPane) {
            stepNameTextView.setText(stepName);
            stepDescTextView.setText(stepDescription);
        }
        if (recipeStep.getVideoURL() == null || recipeStep.getVideoURL().equals("")) {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                    getResources(), R.drawable.app_cover
            ));
            mPlayerView.hideController();
        }

        initializePlayer(Uri.parse(recipeStep.getVideoURL()));
        return rootView;
    }

    public void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
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


    //Resource : Get Thumbnail Video from URL Android
    //Link : https://medium.com/@mujtahidah/get-thumbnail-video-from-url-android-f71533168228
//    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
//            throws Throwable {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever mediaMetadataRetriever = null;
//        try {
//            mediaMetadataRetriever = new MediaMetadataRetriever();
//            if (Build.VERSION.SDK_INT >= 14)
//                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
//            else
//                mediaMetadataRetriever.setDataSource(videoPath);
//
//            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Throwable(
//                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
//                            + e.getMessage());
//
//        } finally {
//            if (mediaMetadataRetriever != null) {
//                mediaMetadataRetriever.release();
//            }
//        }
//        return bitmap;
//    }
}
