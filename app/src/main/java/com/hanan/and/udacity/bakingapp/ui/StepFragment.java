package com.hanan.and.udacity.bakingapp.ui;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.hanan.and.udacity.bakingapp.model.Recipe;
import com.hanan.and.udacity.bakingapp.model.Step;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nono on 3/21/2018.
 */

public class StepFragment extends Fragment {
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private ImageView thumbnailView;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        frameLayout.addView(rootView);
        mPlayerView = rootView.findViewById(R.id.player_view);
        thumbnailView = rootView.findViewById(R.id.thumbnail_view);

        Bundle b = getArguments();
        Step recipeStep = b.getParcelable(Recipe.RECIPE_STEP);
        String stepName = recipeStep.getShortDescription();
        String stepDescription = recipeStep.getDescription();
        String videoUrl = recipeStep.getVideoURL();
        String thumbUrl = recipeStep.getThumbnailURL();

        int width = getResources().getConfiguration().smallestScreenWidthDp;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && width < 600) {
            hideSystemUI();
        } else {
            TextView stepDescTextView = rootView.findViewById(R.id.step_description);
            TextView stepNameTextView = rootView.findViewById(R.id.step_name);

            stepNameTextView.setText(stepName);
            stepDescTextView.setText(stepDescription);
        }
        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt("CURRENT_WINDOW");
            playbackPosition = savedInstanceState.getLong("PLAY_BACK_POSITION");
            playWhenReady = savedInstanceState.getBoolean("PLAY_WHEN_READY");
        }
        initializePlayer(Uri.parse(recipeStep.getVideoURL()));

        if (videoUrl == null || videoUrl.equals("")) {
//            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
//                    getResources(), R.drawable.no_video
//            ));
            thumbnailView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            loadThumbnailImage(thumbUrl);
            mPlayerView.hideController();
        }else{
            thumbnailView.setVisibility(View.GONE);
        }

        return frameLayout;
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
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // Src: https://developer.android.com/training/system-ui/immersive.html
    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("PLAY_BACK_POSITION", playbackPosition);
        outState.putInt("CURRENT_WINDOW", currentWindow);
        outState.putBoolean("PLAY_WHEN_READY", playWhenReady);
    }

    public void loadThumbnailImage(String thumbUrl) {
        Glide.with(this)
                .load(thumbUrl) // image url
                .placeholder(R.drawable.app_cover) // any placeholder to load at start
                .error(R.drawable.no_video)  // any image in case of error
                .fitCenter()
                .into(thumbnailView);
    }
}
