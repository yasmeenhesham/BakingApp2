package com.example.yasmeen.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MenuActivity extends AppCompatActivity implements ListItemClickListener {
    public boolean sort_type=false;
    static String ALL_RECIPES="All_Recipes";
    @NonNull
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       // Toolbar toolbar= (Toolbar) findViewById(R.id.menuToolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setTitle("Backing Time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getIdlingResource();

    }


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onListItemClick(List<Step> stepsOut, int clickedItemIndex, String recipeName) {

    }

    @Override
    public void onListItemClick(Recipe clickedItemIndex) {
        Bundle selectedItem =new Bundle();
        selectedItem.putParcelable("Recipe",clickedItemIndex);
        Intent intent=new Intent(this,RecipeDetailActivity.class);
        intent.putExtras(selectedItem);
        startActivity(intent);

        
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
