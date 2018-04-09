package com.example.yasmeen.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StepFragment extends Fragment {
    private ArrayList<Step> msteps;
    private int selectedIndex;
    private String recipeName;
    private Recipe recipe ;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private ListItemClickListener listItemClickListener;
    public StepFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView;
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        listItemClickListener=( RecipeDetailActivity)getActivity();
        if(savedInstanceState!=null)
        {
            msteps=savedInstanceState.getParcelableArrayList("SELECTED_STEPS");
            selectedIndex=savedInstanceState.getInt("SELECTED_INDEX");
            recipeName = savedInstanceState.getString("Name");
        }
        else {
            msteps =getArguments().getParcelableArrayList("SELECTED_STEPS");

            if(msteps!=null)
            {
                msteps=getArguments().getParcelableArrayList("SELECTED_STEPS");
                selectedIndex=getArguments().getInt("SELECTED_INDEX");
                recipeName=getArguments().getString("Name");
            }
            else {
                recipe =getArguments().getParcelable("Recipe");
                msteps=(ArrayList<Step>) recipe.getSteps();
                selectedIndex=0;
                recipeName=recipe.getName();

            }
        }
        View root= inflater.inflate(R.layout.step_detail,container,false);
        textView =(TextView)root.findViewById(R.id.step_detail_text);
        textView.setText(msteps.get(selectedIndex).getDescription());
        mPlayerView = (SimpleExoPlayerView)root.findViewById(R.id.playerView) ;
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.IsTab))
        {
            ((RecipeDetailActivity) getActivity()).getSupportActionBar().hide();
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        }
        else {
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        }
        String videoURL= msteps.get(selectedIndex).getVideoURL();
        String imgURL =msteps.get(selectedIndex).getThumbnailURL();
        ImageView thumbImage = (ImageView) root.findViewById(R.id.thumbImage);
        if(imgURL!="")
        {
            Uri builtUri = Uri.parse(imgURL).buildUpon().build();
             thumbImage = (ImageView) root.findViewById(R.id.thumbImage);
            Picasso.with(getContext()).load(builtUri).into(thumbImage);
        }
        else
        {
            thumbImage.setVisibility(View.INVISIBLE);
        }
        if(!videoURL.isEmpty())
        {
            initializeExoPlayer(Uri.parse(videoURL));
        }
        else
        {
            mExoPlayer=null;
            mPlayerView.setForeground(ContextCompat.getDrawable(getContext(),R.drawable.question_mark));
        }
        Button previous =(Button)root.findViewById(R.id.previousStep);
        Button next = (Button)root.findViewById(R.id.nextStep);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msteps.get(selectedIndex).getId()>0)
                {
                    if(mExoPlayer!=null)
                        mExoPlayer.stop();
                    listItemClickListener.onListItemClick(msteps,selectedIndex-1,recipeName);
                }
                else {
                    Toast.makeText(getActivity(),"You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msteps.get(selectedIndex).getId()<msteps.get(msteps.size()-1).getId())
                {
                    if(mExoPlayer!=null)
                        mExoPlayer.stop();
                    listItemClickListener.onListItemClick(msteps,msteps.get(selectedIndex).getId()+1,recipeName);
                }
                else {
                    Toast.makeText(getContext(),"You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return root;
    }
    private void initializeExoPlayer(Uri mediaUri) {
        if(mExoPlayer==null)
        {
        TrackSelection.Factory videotrackSelection = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videotrackSelection);
        LoadControl loadControl= new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
        mPlayerView.setPlayer(mExoPlayer);
        String userAgent = Util.getUserAgent(getContext(), "Baking App");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
         }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("SELECTED_STEPS",msteps);
        outState.putInt("SELECTED_INDEX",selectedIndex);
        outState.putString("Title",recipeName);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mExoPlayer!=null)
        {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }
}
