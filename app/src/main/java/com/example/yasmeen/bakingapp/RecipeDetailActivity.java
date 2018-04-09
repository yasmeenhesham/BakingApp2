package com.example.yasmeen.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements ListItemClickListener{

    private Recipe selectedReciepe;
    private String reciepeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(savedInstanceState==null)
        {
            Bundle RecipeBundle = getIntent().getExtras();
            selectedReciepe = RecipeBundle.getParcelable("Recipe");
            reciepeName = selectedReciepe.getName();
            getSupportActionBar().setTitle(reciepeName);
            final DetailFragment detailFragment=new DetailFragment();
            detailFragment.setArguments(RecipeBundle);
            android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container_fragment,detailFragment,"steps").
            addToBackStack("STACK_RECIPE_DETAIL").commit();

            if(getResources().getBoolean(R.bool.IsTab))
            {
                final StepFragment fragment=new StepFragment();
                fragment.setArguments(RecipeBundle);
                manager.beginTransaction()
                        .replace(R.id.container_fragment2, fragment).addToBackStack("STACK_RECIPE_STEP_DETAIL")
                        .commit();
            }


        }
        else {
            reciepeName = savedInstanceState.getString("Name");


        }
        getSupportActionBar().setTitle(reciepeName);

    }

    @Override
    public void onBackPressed() {
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        String tag=fragmentManager.getBackStackEntryAt(fragmentManager
                .getBackStackEntryCount() - 1)
                .getName();
        if(tag=="STACK_RECIPE_DETAIL" || getResources().getBoolean(R.bool.IsTab)) {
            finish();
        }
        else {
            Bundle RecipeBundle = getIntent().getExtras();
            selectedReciepe = RecipeBundle.getParcelable("Recipe");
            reciepeName = selectedReciepe.getName();
            final DetailFragment detailFragment=new DetailFragment();
            detailFragment.setArguments(RecipeBundle);
            fragmentManager.beginTransaction().replace(R.id.container_fragment,detailFragment,"steps").
                    addToBackStack("STACK_RECIPE_DETAIL").commit();
        }
    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int clickedItemIndex, String recipeName) {
        //Toast.makeText(this, "position : "+clickedItemIndex, Toast.LENGTH_LONG).show();
        final StepFragment fragment=new StepFragment();
        android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        getSupportActionBar().setTitle(recipeName);
        Bundle reciepBundle =new Bundle();
        reciepBundle.putParcelableArrayList("SELECTED_STEPS",(ArrayList<Step>) stepsOut);
        reciepBundle.putInt("SELECTED_INDEX",clickedItemIndex);
        reciepBundle.putString("Name",recipeName);
        fragment.setArguments(reciepBundle);
        if(getResources().getBoolean(R.bool.IsTab))
        {
            manager.beginTransaction()
                    .replace(R.id.container_fragment2, fragment,"step").addToBackStack("STACK_RECIPE_STEP_DETAIL")
                    .commit();
        }
        else {
            manager.beginTransaction()
                    .replace(R.id.container_fragment, fragment, "step").addToBackStack("STACK_RECIPE_STEP_DETAIL")
                    .commit();
        }

    }

    @Override
    public void onListItemClick(Recipe clickedItemIndex) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Name",reciepeName);
    }
}
